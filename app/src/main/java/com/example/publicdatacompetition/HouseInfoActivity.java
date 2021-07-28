package com.example.publicdatacompetition;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_START;

public class HouseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private HouseInfoDetail houseInfoDetail;
    private int mIdx;
    private ViewPager viewpager_houseinfo;  // circle indicator 넣어야 함
    private ImageView iv_houseinfo_back, iv_houseinfo_adminprice_guide;
    private TextView tv_houseinfo_title, tv_houseinfo_name, tv_houseinfo_price,tv_houseinfo_dong_housecnt,
            tv_houseinfo_uploaddate, tv_houseinfo_roomtoilet, tv_houseinfo_area, tv_houseinfo_adminprice,
            tv_houseinfo_park, tv_houseinfo_sellername, tv_houseinfo_short_description, tv_houseinfo_nego,
            tv_houseinfo_type, tv_houseinfo_type_price, tv_houseinfo_ratio, tv_houseinfo_ratio_relate,
            tv_houseinfo_brokerfee, tv_houseinfo_resitype, tv_houseinfo_address, tv_houseinfo_dong_ho,
            tv_houseinfo_area_area, tv_houseinfo_room_toilet, tv_houseinfo_park_perhouse, tv_houseinfo_enterdate,
            tv_houseinfo_builddate;
    private CardView cardview_houseinfo_sellerpic;
    private LinearLayout ll_hoseinfo_middledoor, ll_hoseinfo_aircon, ll_hoseinfo_ref, ll_hoseinfo_kimchiref, ll_hoseinfo_clo;
    private AppCompatButton btn_houseinfo_gochat;
    private PhotoView pv_houseinfo_apart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseinfo);

        init();
        getDataFromServer();
        setOptionLayout();
        setDataBinding();
    }

    private void setOptionLayout() {
    }

    private void setDataBinding() {
    }

    private void init() {
        mIdx = getIntent().getIntExtra("idx", -1);

        viewpager_houseinfo = findViewById(R.id.viewpager_houseinfo);
        iv_houseinfo_back = findViewById(R.id.iv_houseinfo_back);
        iv_houseinfo_adminprice_guide = findViewById(R.id.iv_houseinfo_adminprice_guide);

        pv_houseinfo_apart = findViewById(R.id.pv_houseinfo_apart);
        pv_houseinfo_apart.setImageResource(R.drawable.image_apartment);

        tv_houseinfo_title = findViewById(R.id.tv_houseinfo_title);
        tv_houseinfo_name = findViewById(R.id.tv_houseinfo_name);
        tv_houseinfo_price = findViewById(R.id.tv_houseinfo_price);
        tv_houseinfo_dong_housecnt = findViewById(R.id.tv_houseinfo_dong_housecnt);
        tv_houseinfo_uploaddate = findViewById(R.id.tv_houseinfo_uploaddate);
        tv_houseinfo_roomtoilet = findViewById(R.id.tv_houseinfo_roomtoilet);
        tv_houseinfo_area = findViewById(R.id.tv_houseinfo_area);
        tv_houseinfo_adminprice = findViewById(R.id.tv_houseinfo_adminprice);
        tv_houseinfo_park = findViewById(R.id.tv_houseinfo_park);
        tv_houseinfo_sellername = findViewById(R.id.tv_houseinfo_sellername);
        tv_houseinfo_short_description = findViewById(R.id.tv_houseinfo_short_description);
        tv_houseinfo_nego = findViewById(R.id.tv_houseinfo_nego);
        tv_houseinfo_type = findViewById(R.id.tv_houseinfo_type);
        tv_houseinfo_type_price = findViewById(R.id.tv_houseinfo_type_price);
        tv_houseinfo_ratio = findViewById(R.id.tv_houseinfo_ratio);
        tv_houseinfo_ratio_relate = findViewById(R.id.tv_houseinfo_ratio_relate);
        tv_houseinfo_brokerfee = findViewById(R.id.tv_houseinfo_brokerfee);
        tv_houseinfo_resitype = findViewById(R.id.tv_houseinfo_resitype);
        tv_houseinfo_address = findViewById(R.id.tv_houseinfo_address);
        tv_houseinfo_dong_ho = findViewById(R.id.tv_houseinfo_dong_ho);
        tv_houseinfo_area_area = findViewById(R.id.tv_houseinfo_area_area);
        tv_houseinfo_room_toilet = findViewById(R.id.tv_houseinfo_room_toilet);
        tv_houseinfo_park_perhouse = findViewById(R.id.tv_houseinfo_park_perhouse);
        tv_houseinfo_enterdate = findViewById(R.id.tv_houseinfo_enterdate);
        tv_houseinfo_builddate = findViewById(R.id.tv_houseinfo_builddate);
        cardview_houseinfo_sellerpic = findViewById(R.id.cardview_houseinfo_sellerpic);
        ll_hoseinfo_middledoor = findViewById(R.id.ll_hoseinfo_middledoor);
        ll_hoseinfo_aircon = findViewById(R.id.ll_hoseinfo_aircon);
        ll_hoseinfo_ref = findViewById(R.id.ll_hoseinfo_ref);
        ll_hoseinfo_kimchiref = findViewById(R.id.ll_hoseinfo_kimchiref);
        ll_hoseinfo_clo = findViewById(R.id.ll_hoseinfo_clo);
        btn_houseinfo_gochat = findViewById(R.id.btn_houseinfo_gochat);

        iv_houseinfo_back.setOnClickListener(this);
        iv_houseinfo_adminprice_guide.setOnClickListener(this);
        btn_houseinfo_gochat.setOnClickListener(this);
    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit
        if (mIdx == -1) {
            Toast.makeText(this, "글 번호 오류", Toast.LENGTH_SHORT);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_houseinfo_back:
                onBackPressed();
                break;
            case R.id.iv_houseinfo_adminprice_guide:
                // todo : 관리비에 대한 설명 dialog
                break;
            case R.id.btn_houseinfo_gochat:
                // todo : 채팅쪽으로 intent 전달 (putExtra firebase id)
                Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                intent.putExtra("FirebaseId", houseInfoDetail.getMember().getFirebaseId());
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
    }
}