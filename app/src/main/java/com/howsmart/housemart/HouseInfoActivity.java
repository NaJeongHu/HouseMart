package com.howsmart.housemart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Adapter.PagerAdapter_Picture_with_circleindicator;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private HouseInfoDetail houseInfoDetail;
    private int mIdx;
    private ImageView iv_houseinfo_back, iv_houseinfo_adminprice_guide, iv_houseinfo_userprofile;
    private TextView tv_houseinfo_title, tv_houseinfo_name, tv_houseinfo_price,tv_houseinfo_dong_housecnt,
            tv_houseinfo_uploaddate, tv_houseinfo_roomtoilet, tv_houseinfo_area, tv_houseinfo_adminprice,
            tv_houseinfo_park, tv_houseinfo_sellername, tv_houseinfo_short_description, tv_houseinfo_nego,
            tv_houseinfo_type, tv_houseinfo_type_price, tv_houseinfo_ratio, tv_houseinfo_ratio_relate,
            tv_houseinfo_brokerfee, tv_houseinfo_resitype, tv_houseinfo_address, tv_houseinfo_dong_ho,
            tv_houseinfo_area_area, tv_houseinfo_room_toilet, tv_houseinfo_park_perhouse, tv_houseinfo_enterdate,
            tv_houseinfo_builddate, tv_detail_explain_apart, tv_detail_explain_livingroom, tv_detail_explain_kitchen,
            tv_detail_explain_room1, tv_detail_explain_room2, tv_detail_explain_room3, tv_detail_explain_toilet1,
            tv_detail_explain_toilet2, tv_detail_explain_long, tv_detail_explain_intro, tv_houseinfo_adminprice2;
    private CardView cardview_houseinfo_sellerpic;
    private LinearLayout ll_hoseinfo_middledoor, ll_hoseinfo_aircon, ll_hoseinfo_ref, ll_hoseinfo_kimchiref,
            ll_hoseinfo_clo, ll_houseinfo_apart, ll_houseinfo_livingroom, ll_houseinfo_kitchen, ll_houseinfo_room1,
            ll_houseinfo_room2, ll_houseinfo_room3, ll_houseinfo_toilet1, ll_houseinfo_toilet2, ll_hoseinfo_oven, ll_hoseinfo_induction, ll_hoseinfo_airsystem;
    private AppCompatButton btn_houseinfo_gochat;
    private PhotoView pv_houseinfo_apart, pv_houseinfo_livingroom, pv_houseinfo_kitchen, pv_houseinfo_room1,
            pv_houseinfo_room2, pv_houseinfo_room3, pv_houseinfo_toilet1, pv_houseinfo_toilet2, pv_houseinfo_intro;
    private ViewPager viewpager_houseinfo;  // circle indicator 넣어야 함
    private CircleIndicator indicator_houseinfo;
    private PagerAdapter_Picture_with_circleindicator adapter;
    private List<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houseinfo);

        init();
        getDataFromServer();
    }

    private void setViewPagerWithCircleIndicator() {
        urls = new ArrayList<>();
        for (int i=0;i<(houseInfoDetail.getRoom_num() + houseInfoDetail.getToilet_num() + 4);i++) {
            urls.add(houseInfoDetail.getSalesOfferURLS().get(i));
        }

        adapter = new PagerAdapter_Picture_with_circleindicator(urls, this);
        viewpager_houseinfo.setAdapter(adapter);
        indicator_houseinfo.setViewPager(viewpager_houseinfo);
    }

    private void setOptionLayout() {
        switch (houseInfoDetail.getRoom_num()) {
            case 0:
                break;
            case 1:
                ll_houseinfo_room1.setVisibility(View.VISIBLE);
                break;
            case 2:
                ll_houseinfo_room1.setVisibility(View.VISIBLE);
                ll_houseinfo_room2.setVisibility(View.VISIBLE);
                break;
            case 3:
                ll_houseinfo_room1.setVisibility(View.VISIBLE);
                ll_houseinfo_room2.setVisibility(View.VISIBLE);
                ll_houseinfo_room3.setVisibility(View.VISIBLE);
                break;
        }
        switch (houseInfoDetail.getToilet_num()) {
            case 0:
                break;
            case 1:
                ll_houseinfo_toilet1.setVisibility(View.VISIBLE);
                break;
            case 2:
                ll_houseinfo_toilet1.setVisibility(View.VISIBLE);
                ll_houseinfo_toilet2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataBinding() {
        tv_houseinfo_title.setText(houseInfoDetail.getResidence_name());

        // detail_apartment
        long uk = houseInfoDetail.getSale_price() / 100000000;
        long man = (houseInfoDetail.getSale_price() / 10000) % 10000;
        int allnum = Integer.parseInt(houseInfoDetail.getAllnumber());
        int parkingnum = Integer.parseInt(houseInfoDetail.getParkingnumber());
        double parkperhouse = (parkingnum * 1.0) / allnum;
        String parkperhouse_fortext = String.format("%.1f", parkperhouse);
        if (houseInfoDetail.getSale_type().equals("월세")) {
            if (uk > 0) {
                if (man == 0) tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + houseInfoDetail.getMonthly_price() / 10000 + " / " + uk + "억");
                else tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + houseInfoDetail.getMonthly_price() / 10000 + " / " + uk + "억 " + man + "만");
            } else {
                tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + houseInfoDetail.getMonthly_price() / 10000 + " / " + man + "만");
            }
        } else {
            if (uk > 0) {
                if (man == 0) tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + uk + "억");
                else tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + uk + "억 " + man + "만");
            } else {
                tv_houseinfo_price.setText(houseInfoDetail.getSale_type() + " " + man + "만");
            }
        }
        String upload = houseInfoDetail.getLastModifiedDate().substring(0,4) + "." + houseInfoDetail.getLastModifiedDate().substring(4,6) + "." +houseInfoDetail.getLastModifiedDate().substring(6,8) + " 등록";
        String pyeong = String.format("%.1f", houseInfoDetail.getLeaseable_area() / 3.3);
        tv_houseinfo_name.setText(houseInfoDetail.getResidence_name());
        tv_houseinfo_dong_housecnt.setText(houseInfoDetail.getDongri() + " · " + houseInfoDetail.getAllnumber() + "세대");
        tv_houseinfo_uploaddate.setText(upload);
        tv_houseinfo_roomtoilet.setText("방" + houseInfoDetail.getRoom_num() + " / " + "욕실" + houseInfoDetail.getToilet_num());
        tv_houseinfo_area.setText(pyeong + "평형");
        tv_houseinfo_adminprice.setText("관리비 " + houseInfoDetail.getAdmin_expenses() / 10000 + "만원");
        tv_houseinfo_park.setText("세대당 주차 " + parkperhouse_fortext + "대");

        // detail_short_description
        Glide.with(this).load(houseInfoDetail.getMember().getImgUrl()).into(iv_houseinfo_userprofile);
        tv_houseinfo_sellername.setText(houseInfoDetail.getMember().getName());
        tv_houseinfo_short_description.setText(houseInfoDetail.getShort_description());

        // detail_price
        if (houseInfoDetail.isNego()) tv_houseinfo_nego.setText("네고 가능");
        else tv_houseinfo_nego.setText("네고 불가");
        tv_houseinfo_type.setText(houseInfoDetail.getSale_type());
        if (houseInfoDetail.getSale_type().equals("월세")) {
            if (uk > 0) {
                if (man == 0) tv_houseinfo_type_price.setText(houseInfoDetail.getMonthly_price() / 10000 + " / " + uk + "억원");
                else tv_houseinfo_type_price.setText(houseInfoDetail.getMonthly_price() / 10000 + " / " + uk + "억 " + man + "만원");
            } else {
                tv_houseinfo_type_price.setText(houseInfoDetail.getMonthly_price() / 10000 + " / " + man + "만원");
            }
        } else {
            if (uk > 0) {
                if (man == 0) tv_houseinfo_type_price.setText(uk + "억원");
                else tv_houseinfo_type_price.setText(uk + "억 " + man + "만원");
            } else {
                tv_houseinfo_type_price.setText(man + "만원");
            }
        }
        tv_houseinfo_adminprice2.setText(houseInfoDetail.getAdmin_expenses() / 10000 + "만원");
        tv_houseinfo_ratio.setText(houseInfoDetail.getDown_pay_per() + "% / " + houseInfoDetail.getIntermediate_pay_per() + "% / " + houseInfoDetail.getBalance_per() + "%");
        tv_houseinfo_ratio_relate.setText("계약금의 " + houseInfoDetail.getProvisional_down_pay_per()  + "%");
        tv_houseinfo_brokerfee.setText("00만원");

        // detail_detail
        String jungong = houseInfoDetail.getDate().substring(0,4) + "년 " + houseInfoDetail.getDate().substring(4,6) + "월 " + houseInfoDetail.getDate().substring(6,8) + "일";
        // todo : 즉시 입주로 넘어오면 다르게 하게
        String canmove = "";
        if (houseInfoDetail.getMovedate().equals("즉시 입주")) {
            canmove = "즉시 입주";
        } else {
            canmove = houseInfoDetail.getMovedate().substring(0,4) + "년 " + houseInfoDetail.getMovedate().substring(4,6) + "월 " + houseInfoDetail.getMovedate().substring(6,8) + "일";
        }

        tv_houseinfo_resitype.setText(houseInfoDetail.getResidence_type());
        tv_houseinfo_address.setText(houseInfoDetail.getAddress());
        tv_houseinfo_dong_ho.setText(houseInfoDetail.getDong() + "동 / " + houseInfoDetail.getHo() + "호");
        tv_houseinfo_area_area.setText(houseInfoDetail.getNet_leaseable_area() + "m² / " + houseInfoDetail.getLeaseable_area() + "m²");
        tv_houseinfo_room_toilet.setText(houseInfoDetail.getRoom_num() + "개 / " + houseInfoDetail.getToilet_num() + "개");
        tv_houseinfo_park_perhouse.setText(parkperhouse_fortext + "대");
        tv_houseinfo_enterdate.setText(canmove);
        tv_houseinfo_builddate.setText(jungong);

        if (houseInfoDetail.isMiddle_door()) ll_hoseinfo_middledoor.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isAir_conditioner()) ll_hoseinfo_aircon.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isRefrigerator()) ll_hoseinfo_ref.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isKimchi_refrigerator()) ll_hoseinfo_kimchiref.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isCloset()) ll_hoseinfo_clo.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isInduction()) ll_hoseinfo_induction.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isOven()) ll_hoseinfo_oven.setVisibility(View.VISIBLE);
        if (houseInfoDetail.isAirsystem()) ll_hoseinfo_airsystem.setVisibility(View.VISIBLE);

        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(0)).into(pv_houseinfo_apart);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(1)).into(pv_houseinfo_intro);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(2)).into(pv_houseinfo_livingroom);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(3)).into(pv_houseinfo_kitchen);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(4)).into(pv_houseinfo_room1);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(5)).into(pv_houseinfo_room2);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(6)).into(pv_houseinfo_room3);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(7)).into(pv_houseinfo_toilet1);
        Glide.with(this).load(houseInfoDetail.getSalesOfferURLS().get(8)).into(pv_houseinfo_toilet2);

        tv_detail_explain_apart.setText(houseInfoDetail.getApartment_description());
        tv_detail_explain_intro.setText(houseInfoDetail.getPorch_description());
        tv_detail_explain_livingroom.setText(houseInfoDetail.getLivingroom_description());
        tv_detail_explain_kitchen.setText(houseInfoDetail.getKitchen_description());
        tv_detail_explain_room1.setText(houseInfoDetail.getRoom1_description());
        tv_detail_explain_room2.setText(houseInfoDetail.getRoom2_description());
        tv_detail_explain_room3.setText(houseInfoDetail.getRoom3_description());
        tv_detail_explain_toilet1.setText(houseInfoDetail.getToilet1_description());
        tv_detail_explain_toilet2.setText(houseInfoDetail.getToilet2_description());
        tv_detail_explain_long.setText(houseInfoDetail.getLong_description());
    }

    private void init() {
        mIdx = getIntent().getIntExtra("idx", -1);

        iv_houseinfo_back = findViewById(R.id.iv_houseinfo_back);
        iv_houseinfo_adminprice_guide = findViewById(R.id.iv_houseinfo_adminprice_guide);
        iv_houseinfo_userprofile = findViewById(R.id.iv_houseinfo_userprofile);
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
        tv_houseinfo_adminprice2 = findViewById(R.id.tv_houseinfo_adminprice2);
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
        ll_hoseinfo_induction = findViewById(R.id.ll_hoseinfo_induction);
        ll_hoseinfo_oven = findViewById(R.id.ll_hoseinfo_oven);
        ll_hoseinfo_airsystem = findViewById(R.id.ll_hoseinfo_airsystem);
        btn_houseinfo_gochat = findViewById(R.id.btn_houseinfo_gochat);

        tv_detail_explain_apart = findViewById(R.id.tv_detail_explain_apart);
        tv_detail_explain_livingroom = findViewById(R.id.tv_detail_explain_livingroom);
        tv_detail_explain_kitchen = findViewById(R.id.tv_detail_explain_kitchen);
        tv_detail_explain_room1 = findViewById(R.id.tv_detail_explain_room1);
        tv_detail_explain_room2 = findViewById(R.id.tv_detail_explain_room2);
        tv_detail_explain_room3 = findViewById(R.id.tv_detail_explain_room3);
        tv_detail_explain_toilet1 = findViewById(R.id.tv_detail_explain_toilet1);
        tv_detail_explain_toilet2 = findViewById(R.id.tv_detail_explain_toilet2);
        tv_detail_explain_long = findViewById(R.id.tv_detail_explain_long);
        tv_detail_explain_intro = findViewById(R.id.tv_detail_explain_intro);
        ll_houseinfo_apart = findViewById(R.id.ll_houseinfo_apart);
        ll_houseinfo_livingroom = findViewById(R.id.ll_houseinfo_livingroom);
        ll_houseinfo_kitchen = findViewById(R.id.ll_houseinfo_kitchen);
        ll_houseinfo_room1 = findViewById(R.id.ll_houseinfo_room1);
        ll_houseinfo_room2 = findViewById(R.id.ll_houseinfo_room2);
        ll_houseinfo_room3 = findViewById(R.id.ll_houseinfo_room3);
        ll_houseinfo_toilet1 = findViewById(R.id.ll_houseinfo_toilet1);
        ll_houseinfo_toilet2 = findViewById(R.id.ll_houseinfo_toilet2);

        pv_houseinfo_apart = findViewById(R.id.pv_houseinfo_apart);
        pv_houseinfo_apart.setImageResource(R.drawable.image_apartment);    // 예시로 둔 거고 나중엔 위아래 공백이랑 여기 줄도 삭제

        pv_houseinfo_livingroom = findViewById(R.id.pv_houseinfo_livingroom);
        pv_houseinfo_kitchen = findViewById(R.id.pv_houseinfo_kitchen);
        pv_houseinfo_room1 = findViewById(R.id.pv_houseinfo_room1);
        pv_houseinfo_room2 = findViewById(R.id.pv_houseinfo_room2);
        pv_houseinfo_room3 = findViewById(R.id.pv_houseinfo_room3);
        pv_houseinfo_toilet1 = findViewById(R.id.pv_houseinfo_toilet1);
        pv_houseinfo_toilet2 = findViewById(R.id.pv_houseinfo_toilet2);
        pv_houseinfo_intro = findViewById(R.id.pv_houseinfo_intro);

        viewpager_houseinfo = findViewById(R.id.viewpager_houseinfo);
        indicator_houseinfo = findViewById(R.id.indicator_houseinfo);

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
                    setOptionLayout();
                    setDataBinding();
                    setViewPagerWithCircleIndicator();
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
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("FirebaseId", houseInfoDetail.getMember().getFirebaseId());
                intent.putExtra("houseIdx",houseInfoDetail.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
    }
}