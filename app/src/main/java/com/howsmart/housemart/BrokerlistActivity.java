package com.howsmart.housemart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.howsmart.housemart.Adapter.RecyclerViewAdapter_MyContract;
import com.howsmart.housemart.Model.MyContract;
import com.howsmart.housemart.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrokerlistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_MyContract adapter;
    private ArrayList<MyContract> arr = new ArrayList<>();
    private LinearLayoutManager manager;
    private LinearLayout ll_brokerlist_nothing;
    private ImageView btn_back;
    private String userId;
    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brokerlist);

        ll_brokerlist_nothing = findViewById(R.id.ll_brokerlist_nothing);
        recyclerView = findViewById(R.id.recycler_view_brokerlist);
        btn_back = findViewById(R.id.btn_back_brokerlist);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mUser = (User) getIntent().getSerializableExtra("user");
        userId = mUser.getUserId();
        manager = new LinearLayoutManager(BrokerlistActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        getDataFromServer();


    }

    private void getDataFromServer() {

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getBrokerContractList(userId).enqueue(new Callback<List<MyContract>>() {
            @Override
            public void onResponse(Call<List<MyContract>> call, Response<List<MyContract>> response) {

                List<MyContract> Result = (List<MyContract>) response.body();

                arr = (ArrayList) Result;
                connectToAdapter();
            }

            @Override
            public void onFailure(Call<List<MyContract>> call, Throwable throwable) {

            }
        });
    }

    private void connectToAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arr.isEmpty() == false || arr.size() != 0) {
                            ll_brokerlist_nothing.setVisibility(View.GONE);
                            adapter = new RecyclerViewAdapter_MyContract(getApplicationContext(), arr);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                            ll_brokerlist_nothing.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

