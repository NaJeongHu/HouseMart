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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Adapter.PagerAdapter_Picture_with_circleindicator;
import com.github.chrisbanes.photoview.PhotoView;
import com.howsmart.housemart.Adapter.RecyclerViewAdapter_Realprice;
import com.howsmart.housemart.Model.Realprice;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
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
            tv_detail_explain_toilet2, tv_detail_explain_long, tv_detail_explain_intro, tv_houseinfo_adminprice2
            ,tv_name_realprice,tv_price1_realprice,tv_price2_realprice;
    private CardView cardview_houseinfo_sellerpic;
    private LinearLayout ll_hoseinfo_middledoor, ll_hoseinfo_aircon, ll_hoseinfo_ref, ll_hoseinfo_kimchiref,
            ll_hoseinfo_clo, ll_houseinfo_apart, ll_houseinfo_livingroom, ll_houseinfo_kitchen, ll_houseinfo_room1,
            ll_houseinfo_room2, ll_houseinfo_room3, ll_houseinfo_toilet1, ll_houseinfo_toilet2, ll_hoseinfo_oven, ll_hoseinfo_induction, ll_hoseinfo_airsystem
            ,ll_realprice_show,ll_realprice_noshow,ll_realprice;
    private AppCompatButton btn_houseinfo_gochat,btn_typesell,btn_typeothers;
    private PhotoView pv_houseinfo_apart, pv_houseinfo_livingroom, pv_houseinfo_kitchen, pv_houseinfo_room1,
            pv_houseinfo_room2, pv_houseinfo_room3, pv_houseinfo_toilet1, pv_houseinfo_toilet2, pv_houseinfo_intro;
    private ViewPager viewpager_houseinfo;  // circle indicator 넣어야 함
    private CircleIndicator indicator_houseinfo;
    private PagerAdapter_Picture_with_circleindicator adapter;
    private List<String> urls;

    private RecyclerViewAdapter_Realprice adapter_realprice;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    ArrayList<Realprice> arr = null;

    List<String[]> list = null;
    List<String[]> list1 = null;


    private String realpricetype;

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

        recyclerView = findViewById(R.id.recycler_realprice);
        realpricetype="매매";

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
        tv_price1_realprice = findViewById(R.id.tv_price1_realprice);
        tv_price2_realprice = findViewById(R.id.tv_price2_realprice);
        tv_name_realprice = findViewById(R.id.tv_name_realprice);
        cardview_houseinfo_sellerpic = findViewById(R.id.cardview_houseinfo_sellerpic);
        ll_hoseinfo_middledoor = findViewById(R.id.ll_hoseinfo_middledoor);
        ll_hoseinfo_aircon = findViewById(R.id.ll_hoseinfo_aircon);
        ll_hoseinfo_ref = findViewById(R.id.ll_hoseinfo_ref);
        ll_hoseinfo_kimchiref = findViewById(R.id.ll_hoseinfo_kimchiref);
        ll_hoseinfo_clo = findViewById(R.id.ll_hoseinfo_clo);
        ll_hoseinfo_induction = findViewById(R.id.ll_hoseinfo_induction);
        ll_hoseinfo_oven = findViewById(R.id.ll_hoseinfo_oven);
        ll_hoseinfo_airsystem = findViewById(R.id.ll_hoseinfo_airsystem);
        ll_realprice = findViewById(R.id.ll_realprice);
        ll_realprice_show = findViewById(R.id.ll_realprice_show);
        ll_realprice_noshow = findViewById(R.id.ll_realprice_noshow);
        btn_houseinfo_gochat = findViewById(R.id.btn_houseinfo_gochat);
        btn_typeothers = findViewById(R.id.btn_typeothers);
        btn_typesell = findViewById(R.id.btn_typesell);

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
        btn_typesell.setOnClickListener(this);
        btn_typeothers.setOnClickListener(this);

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

            case R.id.btn_typesell:
                if (!realpricetype.equals("매매")) {
                    btn_typesell.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_blue));
                    btn_typesell.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_typeothers.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_whitegray));
                    btn_typeothers.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    realpricetype = "매매";
                    renewlist();
                }
                break;

            case R.id.btn_typeothers:
                if (!realpricetype.equals("전/월세")) {
                    btn_typesell.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_whitegray));
                    btn_typesell.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_typeothers.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_rectangle_blue));
                    btn_typeothers.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    realpricetype = "전/월세";
                    renewlist();
                }
                break;
        }
    }

    public void renewlist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                arr = new ArrayList<>();
                readDataFromCsv();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(arr.size()==0){
                            ll_realprice_show.setVisibility(View.GONE);
                            ll_realprice_noshow.setVisibility(View.VISIBLE);
                        }
                        else{
                            ll_realprice_show.setVisibility(View.VISIBLE);
                            ll_realprice_noshow.setVisibility(View.GONE);
                            adapter_realprice = new RecyclerViewAdapter_Realprice(getApplicationContext(), arr);
                            recyclerView.setAdapter(adapter_realprice);
                            adapter_realprice.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

    public void readDataFromCsv() {
        if (list1 == null) {
            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.realprice1));
            CSVReader reader = new CSVReader(is);
            try {
                list1 = reader.readAll();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        if(list2==null) {
//            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.realprice2));
//            CSVReader reader = new CSVReader(is);
//            try {
//                list2 = reader.readAll();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if(list3==null){
//            list3=list1;
//            list3.addAll(list2);
//        }
        if (arr.size() != 0) {
            arr.clear();
            list.clear();
        }

//        if (realpricetype.equals("매매")){
//            list = list1;
//        }
//        else{
//            list = list2;
//        }

        list = list1;

        for (String[] str : list) {

            Realprice entity = new Realprice();
            entity.setNet_leaseable_area(str[1]);
            entity.setContract_date(str[2] + str[3]);
            entity.setSale_price(str[4]);
            entity.setFloor(str[5]);
            entity.setSale_type("매매");

//            if(str.length>7){
//                entity.setMonthly_price(str[7]);
//                entity.setSale_type(str[8]);
//            }
//            else{
//                entity.setSale_type("매매");
//            }

            if (!str[6].equals(" ")) {
                if (houseInfoDetail.getAddress().contains(str[6])) {  // 검색어가 포함된 경우
                    arr.add(entity); //entity 이름이 search를 포함하기 때문에 arr에 추가
                }
            } else {
                adapter_realprice = null; //검색하는 것이 없다면 adapter을 null로 만드나??
            }
        }
    }

    public void realPrice() {
        double sum = 0.0;
        int num = 0;
        tv_name_realprice.setText(houseInfoDetail.getResidence_name() +" "+ houseInfoDetail.getNet_leaseable_area()+"m²");
        if(arr!=null) {
            for (int i = 0; i < arr.size(); i++) {
                int temp = changeArea(arr.get(i).getNet_leaseable_area());
                if (temp == Integer.parseInt(String.valueOf(Math.round(houseInfoDetail.getNet_leaseable_area())))) {
                    sum += changePrice(arr.get(i).getSale_price());
                    num++;
                }
            }
        }
        if(num==0){
            tv_price1_realprice.setText("최근 1년간 매매 거래내역이 없습니다");
            tv_price2_realprice.setText("");
        }
        else {
            sum /= num;
            setrealrPrice(sum);
        }
    }

    public void setrealrPrice(double average) {
        int av = (int) average;
        int av2 = (int)(3.3*average/Integer.parseInt(String.valueOf(Math.round(houseInfoDetail.getNet_leaseable_area()))));
        tv_price1_realprice.setText(translatePrice2(av));
        tv_price2_realprice.setText(translatePrice2(av2));
    }

    public int changeArea(String area) {
        Double temp = Double.parseDouble(area);
        return Integer.parseInt(String.valueOf(Math.round(temp)));
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
                    renewlist();
                    realPrice();
                    ll_realprice.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<HouseInfoDetail> call, Throwable throwable) {
                    Log.d("ListActivity 통신 실패", "");
                }
            });
        }
    }

    public int changePrice(String price) {

        String a = "", b = "", c = "";
        a = price.substring(0, price.indexOf(","));
        b = price.substring(price.indexOf(",") + 1);
        return Integer.parseInt(a + b);
    }

    public String translatePrice2(int price) {

        String a = "", b = "", c = "";
        if (price == 0) {
            return "0원";
        }
        if (price >= 10000) {
            int price1 = price / 10000;
            price %= 10000;
            a = price1 + "억 ";
        }
        if (price > 0) {
            b = price + "만";
        }
        return a + b + "원";
    }
}