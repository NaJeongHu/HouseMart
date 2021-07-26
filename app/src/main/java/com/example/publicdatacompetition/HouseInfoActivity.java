package com.example.publicdatacompetition;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseInfoActivity extends AppCompatActivity {

    private ArrayList<PermittedHouse> houseInfoDetail;
    private int mIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseinfo);

        init();

        getDataFromServer();
    }

    private void init() {

        mIdx = Integer.parseInt(getIntent().getStringExtra("idx"));
    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getDetailInfo(Long.valueOf(mIdx)).enqueue(new Callback<List<HouseInfoDetail>>() {
            @Override
            public void onResponse(Call<List<HouseInfoDetail>> call, Response<List<HouseInfoDetail>> response) {
                List<HouseInfoDetail> Result = (List<HouseInfoDetail>) response.body();
                houseInfoDetail = (ArrayList) Result;
                // todo : 리스트 받은 거로 사진 url만 따로 리스트 만들어서 뷰페이저 어뎁터 연결

            }

            @Override
            public void onFailure(Call<List<HouseInfoDetail>> call, Throwable throwable) {
                Log.d("ListActivity 통신 실패", "");
            }
        });

    }

}
