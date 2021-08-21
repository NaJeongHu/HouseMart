package com.howsmart.housemart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.howsmart.housemart.Adapter.RecyclerViewAdapter_MyContract;
import com.howsmart.housemart.Adapter.RecyclerViewAdapter_MySell;
import com.howsmart.housemart.Model.Contract;
import com.howsmart.housemart.Model.MyContract;
import com.howsmart.housemart.Model.MyHouse;
import com.howsmart.housemart.Model.PermittedHouse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Contract extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_MyContract adapter;
    private ArrayList<MyContract> arr = new ArrayList<>();
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_contract, container, false);

        recyclerView =rootView.findViewById(R.id.recycler_contract);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;

    }
    private void getDataFromServer() {

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getAllContractList(userId).enqueue(new Callback<List<MyContract>>() {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (arr.isEmpty() == false || arr.size() != 0) {
                            adapter = new RecyclerViewAdapter_MyContract(getActivity(), arr);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
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