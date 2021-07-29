package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private String mSido;
    private String mSigungu;
    private String mSearch;
    private String mSubject;
    private FloatingActionButton mToMap, mToTop;
    private Button mRegionButton, mSearchButton;
    private ImageView mBackButton, mFilterButton;
    private TextView mItemCount, mToolbarTitle;
    private ArrayList<PermittedHouse> PermittedList;
    private ListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
        addScrollListenerOnRecyclerView();
        addItemTouchListenerOnRecyclerView();
        getDataFromServer();
    }

    // when user scroll recycler
    private void addScrollListenerOnRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mManager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    mToTop.setVisibility(View.VISIBLE);
                } else {
                    mToTop.setVisibility(View.GONE);
                }
            }
        });
    }

    // when user touch item
    private void addItemTouchListenerOnRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), mRecyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), HouseInfoActivity.class);
                        intent.putExtra("idx",PermittedList.get(position).getIdx());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_tomap:
                Intent intent1 = new Intent(getApplicationContext(), MapActivity.class);
                intent1.putExtra("search", mSearch);
                intent1.putExtra("subject", mSubject);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_list_region:
                Intent intent = new Intent(getApplicationContext(), SidoActivity.class);
                intent.putExtra("search", mSearch);
                intent.putExtra("subject", mSubject);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_list_back:
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.iv_list_filter:
                Intent intent2 = new Intent(getApplicationContext(), FilterActivity.class);
                intent2.putExtra("search", mSearch);
                intent2.putExtra("subject", mSubject);
                startActivity(intent2);
                break;
            case R.id.btn_list_search:
                Intent intent3 = new Intent(getApplicationContext(), SearchActivity.class);
                intent3.putExtra("Imfrom", "recyclerview");
                intent3.putExtra("subject", mSubject);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
    }

    private void init() {
        mManager = new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mToMap = findViewById(R.id.fb_tomap);
        mToTop = findViewById(R.id.fb_totop);
        mRegionButton = findViewById(R.id.btn_list_region);
        mSearchButton = findViewById(R.id.btn_list_search);
        mBackButton = findViewById(R.id.btn_list_back);
        mFilterButton = findViewById(R.id.iv_list_filter);
        mItemCount = findViewById(R.id.tv_list_itemcount);
        mToolbarTitle = findViewById(R.id.tv_list_title);

        mRecyclerView.setLayoutManager(mManager);
        mToMap.setOnClickListener(this);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFilterButton.setOnClickListener(this);

        mSido = MyLocation.getInstance().getSIDO();
        mSigungu = MyLocation.getInstance().getSIGUNGU();
        mSubject = getIntent().getStringExtra("subject");
        mSearch = getIntent().getStringExtra("search");

        setToolbarTitle();
    }

    private void setToolbarTitle() {
        mToolbarTitle.setText(mSubject);

        String name;
        if (mSido.length() == 4) {
            name = "" + mSido.charAt(0) + mSido.charAt(2);
        } else {
            name = mSido.substring(0, 2);
        }
        if (mSido != null && mSigungu != null) {
            mRegionButton.setText(" " + name + " - " + mSigungu + " ");
        }
    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getList().enqueue(new Callback<List<PermittedHouse>>() {
            @Override
            public void onResponse(Call<List<PermittedHouse>> call, Response<List<PermittedHouse>> response) {
                List<PermittedHouse> Result = (List<PermittedHouse>) response.body();
                PermittedList = (ArrayList) Result;
                mItemCount.setText(PermittedList.size() + "채의 집을 구경하세요");
                connectToAdapter();
//                Log.d("PermittedList", String.valueOf(PermittedList.get(0)));
//                Log.d("PermittedList", PermittedList.get(0).getResidence_name());
//                Log.d("PermittedList", "" + PermittedList.get(0).getTitleImg());
            }

            @Override
            public void onFailure(Call<List<PermittedHouse>> call, Throwable throwable) {
                Log.d("ListActivity 통신 실패", "");
            }
        });


        // todo : 1. 세진님이 upload activity, filter 완성하기
        // todo : 2. 진아님이 unchecked house 검증하기
        // todo : 3. 정후님이 houseinfo activity 연결하기
        // todo : 4. 진아님이 상세매물에서 채팅 연결하기
        // todo : 5. 진아님이 채팅에서 가계약 버튼 만들기
        // todo : 6. 세진님이 회사계좌로 가계약금 수신 및 송신, 서버에 거래 상황 갱신
        // todo : 7. 정후님이 거래중인 내역 List view, detail layout 완성, data binding
        // todo : 8. 세진님이 공인중개사 인증기능, 공인중개사 매물 list, 중개하기, 중개완료 구현

        // todo : final. Design


        // TODO: 2021-07-23 connect adapter on another thread

    }

    private void connectToAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(PermittedList.isEmpty() == false || PermittedList.size() != 0){
//                            Collections.sort(arr,new Filtering_for_ganada());
                            mItemCount.setText(PermittedList.size()+ "채의 아파트를 나열했어요");
                            adapter = new ListRecyclerAdapter(getApplicationContext(), PermittedList);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else if(PermittedList.size() == 0){
                            mItemCount.setText("검색 결과가 없어요.");
                        }
//                        base_progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
}
