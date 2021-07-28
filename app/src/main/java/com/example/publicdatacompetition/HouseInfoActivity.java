package com.example.publicdatacompetition;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseInfoActivity extends AppCompatActivity {

    private HouseInfoDetail houseInfoDetail;
    private int mIdx;
    private ViewPager viewpager_houseinfo;  // circle indicator 넣어야 함
    private ImageView btn_houseinfo_back;
    private TextView tv_houseinfo_title, tv_houseinfo_name, tv_houseinfo_price, tv_houseinfo_shortd, tv_houseinfo_uploaddate, tv_houseinfo_roomtoilet, tv_houseinfo_area, tv_houseinfo_adminprice, tv_houseinfo_park;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseinfo);

        init();

        getDataFromServer();
    }

    private void init() {
        mIdx = getIntent().getIntExtra("idx",-1);



    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit
        if (mIdx == -1) {
            Toast.makeText(this,"글 번호 오류",Toast.LENGTH_SHORT);
        } else {
            RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
            mRESTApi.getDetailInfo(Long.valueOf(mIdx)).enqueue(new Callback<HouseInfoDetail>() {
                @Override
                public void onResponse(Call<HouseInfoDetail> call, Response<HouseInfoDetail> response) {
                    HouseInfoDetail Result = (HouseInfoDetail) response.body();
                    houseInfoDetail = Result;
                    Log.d("HouseInfoActivity 통신 성공", houseInfoDetail.toString());
                    // todo : 리스트 받은 거로 사진 url만 따로 리스트 만들어서 뷰페이저 어뎁터 연결

                }

                @Override
                public void onFailure(Call<HouseInfoDetail> call, Throwable throwable) {
                    Log.d("ListActivity 통신 실패", "");
                }
            });
        }
    }

}
