package com.howsmart.housemart;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.L;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.howsmart.housemart.Adapter.PagerAdapter_Picture;
import com.howsmart.housemart.Adapter.RecyclerViewAdapter_Realprice;
import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.Pictures;
import com.howsmart.housemart.Model.Realprice;
import com.howsmart.housemart.Model.User;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, PagerAdapter_Picture.OnItemClick {

    private ImageView btn_back;
    private AppCompatButton btn_type1, btn_type2, btn_type3, btn_no_dialog_upload, btn_okay_dialog_upload, btn_typesell, btn_typeothers;
    private LinearLayout lin_price_month,ll_realprice,ll_realprice_show,ll_realprice_noshow;
    private TextInputEditText edit_dong, edit_ho, edit_area1, edit_area2, edit_price_all, edit_price_month, edit_price_manage,
            edit_introduce_short, edit_introduce_long, edit_room, edit_toilet, edit_introduce_livingroom, edit_introduce_kitchen,
            edit_introduce_room1, edit_introduce_room2, edit_introduce_room3, edit_introduce_toilet1, edit_introduce_toilet2, edit_introduce_apartment;
    private TextView tv_area1, tv_area2, tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_price_manage, tv_complete, edit_apartment, tv_movedate, tv_apartaddress_load
            ,tv_name_realprice,tv_price1_realprice,tv_price2_realprice;
    private RangeSlider slider_ratio1, slider_ratio2;
    private CheckBox chk_nego, chk_door, chk_air, chk_ref, chk_kimchi, chk_closet, chk_oven, chk_induction, chk_airsystem, chk_movenow;
    private Calendar cal;
    private LineChart chart;
    private LineGraph lineGraph;

    private long price_first, price_second, price_third;

    private int picture_clicked_position = 0;

    private PagerAdapter_Picture pagerAdapter_picture;

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private ViewPager viewPager;
    private PagerAdapter_Picture adapter;
    private List<Pictures> models;
    private List<String> models_description;

    House mHouse;

    private static final int IMAGE_REQUEST = 0;
    private static final int NAME_REQUEST = 1;
    private Uri imageUri;

    private String userId;//사용자 이름
    private String residence_name;//아파트 이름
    private String code;//아파트 코드
    private String address;//도로명 주소
    private String sido;//시도
    private String sigungoo;//시군구
    private String dongri;//동리
    private String date;//사용승인일일
    private Integer allnumber;//세대수
    private Integer parkingnumber;//총주차대수
    private String contact;//관리사무소 연락처

    private Integer dong, ho;//동,호수
    private Double net_leaseable_area;//전용면적
    private Double leaseable_area;//공급면적

    private String residence_type;//매물 타입(A,V,O)
    private String sale_type;//"월세","전세","매매"
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private Long admin_expenses;//관리비

    private Integer provisional_down_pay_per = 10;//가계약금 비율
    private Integer down_pay_per = 10;//계약금 비율
    private Integer intermediate_pay_per = 30;//중도금 비율
    private Integer balance_per = 60;//잔금 비율

    private Integer room_num;//방 개수
    private Integer toilet_num;//욕실 개수

    private boolean middle_door;//중문
    private boolean air_conditioner;//시스템 에어컨
    private boolean refrigerator;//냉장고
    private boolean kimchi_refrigerator;//김치냉장고
    private boolean closet;//붙박이장
    private boolean oven;//빌트인 오븐
    private boolean induction;//인덕션
    private boolean airsystem;//공조기 시스템

    private boolean nego;//네고가능

    private String short_description;//짧은 집 소개
    private String long_description;//긴 집 소개
    private String apartment_description;//아파트 소개
    private String livingroom_description;//거실 소개
    private String kitchen_description;//주방 소개
    private String room1_description;//방1 소개
    private String room2_description;//방2 소개
    private String room3_description;//방3 소개
    private String toilet1_description;//화장실1 소개
    private String toilet2_description;//화장실2 소개

    private String movedate;//입주가능일

    private List<MultipartBody.Part> pictures;
    private int roomcnt;
    private int toiletcnt;

    private User mUser;

    private RecyclerViewAdapter_Realprice adapter_realprice;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    ArrayList<Realprice> arr = null;

    List<String[]> list = null;
    List<String[]> list1 = null; //매매
    List<String[]> list2 = null;
    List<String[]> list3 = null;


    private String realpricetype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        init();

        roomcnt = Integer.parseInt(getIntent().getStringExtra("roomcnt"));
        toiletcnt = Integer.parseInt(getIntent().getStringExtra("toiletcnt"));
        models = new ArrayList<>();
        models.add(new Pictures("아파트 외관 사진을 등록해주세요", R.drawable.image_apartment, "위치, 주변 시설, 아파트 정원 등등 상세하게 적어주세요", "아파트 소개"));
        models.add(new Pictures("현관 사진을 등록해주세요", R.drawable.image_frontdoor, "현관 크기, 확장 여부, 신발장 공간 등등 상세하게 적어주세요", "현관 소개"));
        models.add(new Pictures("거실 사진을 등록해주세요", R.drawable.image_livingroom, "베란다 확장 여부, 방향, 바닥재질 등등 상세하게 적어주세요", "거실 소개"));
        models.add(new Pictures("주방 사진을 등록해주세요", R.drawable.image_kitchen, "수납공간, 식탁 여부, 인테리어 등등 상세하게 적어주세요", "주방 소개"));
        for (int i = 1; i <= roomcnt; i++) {
            models.add(new Pictures(i + "번 방" + " 사진을 등록해주세요", R.drawable.image_room4, "채광이나 방향, 벽지 상태 등등 상세하게 적어주세요", i + "번 방 소개"));
        }
        for (int i = 1; i <= toiletcnt; i++) {
            models.add(new Pictures(i + "번 화장실" + " 사진을 등록해주세요", R.drawable.image_toilet1, "수압, 환풍기 상태, 습식/건식 여부 등등 상세하게 적어주세요", i + "번 화장실 소개"));
        }

        pagerAdapter_picture = new PagerAdapter_Picture();

        adapter = new PagerAdapter_Picture(models, this, this);

        viewPager = findViewById(R.id.viewPager_upload_picture);
        int dpValue = 54;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin / 3);

        viewPager.setAdapter(adapter);

    }

    private void init() {

        recyclerView = findViewById(R.id.recycler_realprice);
        manager = new LinearLayoutManager(UploadActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        cal = Calendar.getInstance();

        btn_back = findViewById(R.id.btn_back_upload);

        btn_type1 = findViewById(R.id.btn_type1);
        btn_type2 = findViewById(R.id.btn_type2);
        btn_type3 = findViewById(R.id.btn_type3);
        btn_typesell = findViewById(R.id.btn_typesell);
        btn_typeothers = findViewById(R.id.btn_typeothers);

        lin_price_month = findViewById(R.id.lin_price_month);
        ll_realprice = findViewById(R.id.ll_realprice);
        ll_realprice.setVisibility(View.GONE);
        ll_realprice_show = findViewById(R.id.ll_realprice_show);
        ll_realprice_noshow = findViewById(R.id.ll_realprice_noshow);


        edit_apartment = findViewById(R.id.edit_apartment);
        edit_dong = findViewById(R.id.edit_dong);
        edit_ho = findViewById(R.id.edit_ho);
        edit_area1 = findViewById(R.id.edit_area1);
        edit_area2 = findViewById(R.id.edit_area2);
        edit_price_all = findViewById(R.id.edit_price_all);
        edit_price_month = findViewById(R.id.edit_price_month);
        edit_price_manage = findViewById(R.id.edit_price_manage);
//        edit_room = findViewById(R.id.edit_room);
//        edit_toilet = findViewById(R.id.edit_toilet);
        edit_introduce_short = findViewById(R.id.edit_introduce_short);
        edit_introduce_long = findViewById(R.id.edit_introduce_long);
//        edit_introduce_apartment = findViewById(R.id.edit_introduce_apartment);
//        edit_introduce_livingroom= findViewById(R.id.edit_introduce_livingroom);
//        edit_introduce_kitchen = findViewById(R.id.edit_introduce_kitchen);
//        edit_introduce_room1 = findViewById(R.id.edit_introduce_room1);
//        edit_introduce_room2 = findViewById(R.id.edit_introduce_room2);
//        edit_introduce_room3 = findViewById(R.id.edit_introduce_room3);
//        edit_introduce_toilet1 = findViewById(R.id.edit_introduce_toilet1);
//        edit_introduce_toilet2 = findViewById(R.id.edit_introduce_toilet2);

        tv_area1 = findViewById(R.id.tv_area1);
        tv_area2 = findViewById(R.id.tv_area2);
        tv_ratio1 = findViewById(R.id.tv_ratio1);
        tv_ratio2 = findViewById(R.id.tv_ratio2);
        tv_price_ratio1 = findViewById(R.id.tv_price_ratio1);
        tv_price_ratio2 = findViewById(R.id.tv_price_ratio2);
        tv_price_type = findViewById(R.id.tv_price_type);
        tv_price_all = findViewById(R.id.tv_price_all);
        tv_price_month = findViewById(R.id.tv_price_month);
        tv_price_manage = findViewById(R.id.tv_price_manage);
        tv_complete = findViewById(R.id.tv_complete);
        tv_movedate = findViewById(R.id.tv_movedate);
        tv_apartaddress_load = findViewById(R.id.tv_apartaddress_load);
        tv_name_realprice = findViewById(R.id.tv_name_realprice);
        tv_price1_realprice = findViewById(R.id.tv_price1_realprice);
        tv_price2_realprice = findViewById(R.id.tv_price2_realprice);

        slider_ratio1 = findViewById(R.id.slider_ratio1);
        slider_ratio2 = findViewById(R.id.slider_ratio2);

        chk_nego = findViewById(R.id.chk_nego);
        chk_door = findViewById(R.id.chk_door);
        chk_air = findViewById(R.id.chk_air);
        chk_ref = findViewById(R.id.chk_ref);
        chk_kimchi = findViewById(R.id.chk_kimchi);
        chk_closet = findViewById(R.id.chk_closet);
        chk_oven = findViewById(R.id.chk_oven);
        chk_induction = findViewById(R.id.chk_induction);
        chk_airsystem = findViewById(R.id.chk_airsystem);
        chk_movenow = findViewById(R.id.chk_movenow);
        chart = findViewById(R.id.lineChart);
        lineGraph = new LineGraph(UploadActivity.this, chart);

        btn_back.setOnClickListener(this);
        btn_type1.setOnClickListener(this);
        btn_type2.setOnClickListener(this);
        btn_type3.setOnClickListener(this);
        btn_typesell.setOnClickListener(this);
        btn_typeothers.setOnClickListener(this);

        chk_nego.setOnClickListener(this);
        chk_door.setOnClickListener(this);
        chk_air.setOnClickListener(this);
        chk_ref.setOnClickListener(this);
        chk_kimchi.setOnClickListener(this);
        chk_closet.setOnClickListener(this);
        chk_oven.setOnClickListener(this);
        chk_induction.setOnClickListener(this);
        chk_airsystem.setOnClickListener(this);
        chk_movenow.setOnClickListener(this);
        tv_complete.setOnClickListener(this);
        edit_apartment.setOnClickListener(this);
        tv_movedate.setOnClickListener(this);

        residence_type = "아파트";
        sale_type = "월세";
        realpricetype = "매매";

        nego = false;
        air_conditioner = false;
        middle_door = false;
        refrigerator = false;
        kimchi_refrigerator = false;
        closet = false;

        price_first = -1;
        sale_price = -1L;
        net_leaseable_area=0.0;

        mUser = (User) getIntent().getSerializableExtra("user");

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String d, m;
                if (dayOfMonth < 10) {
                    d = "0" + dayOfMonth;
                } else {
                    d = "" + dayOfMonth;
                }
                month++;//이상하게 월이 하나 적게 들어옴..
                if (month < 10) {
                    m = "0" + month;
                } else {
                    m = "" + month;
                }
                movedate = year + m + d;
                tv_movedate.setText(year + "년 " + month + "월 " + dayOfMonth + "일");
            }
        };

        edit_dong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_dong.getText().toString().equals("") || edit_dong.getText().toString() == null) {

                } else {
                    dong = Integer.parseInt(edit_dong.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_ho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_ho.getText().toString().equals("") || edit_ho.getText().toString() == null) {

                } else {
                    ho = Integer.parseInt(edit_ho.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_introduce_short.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_introduce_short.getText().toString().equals("") || edit_introduce_short.getText().toString() == null) {

                } else {
                    short_description = edit_introduce_short.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_introduce_long.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_introduce_long.getText().toString().equals("") || edit_introduce_long.getText().toString() == null) {

                } else {
                    long_description = edit_introduce_long.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_area1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area1.getText().toString().equals("") || edit_area1.getText().toString() == null) {
                    tv_area1.setText("평수");
                } else {
                    try {
                        net_leaseable_area = Double.parseDouble(edit_area1.getText().toString().trim());
                        tv_area1.setText(translateArea(net_leaseable_area));
                        realPrice();
                    } catch (NumberFormatException e) {
                        tv_area1.setText("평수");
                        Toast.makeText(getBaseContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_area2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area2.getText().toString().equals("") || edit_area2.getText().toString() == null) {
                    tv_area2.setText("평수");
                } else {
                    try {
                        leaseable_area = Double.parseDouble(edit_area2.getText().toString().trim());
                        tv_area2.setText(translateArea(leaseable_area));
                    } catch (NumberFormatException e) {
                        tv_area2.setText("평수");
                        Toast.makeText(getBaseContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_price_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_all.getText().toString().equals("") || edit_price_all.getText().toString() == null) {
                    tv_price_all.setText("가격");
                } else {
                    sale_price = Long.parseLong(edit_price_all.getText().toString().trim());
                    tv_price_all.setText(translatePrice(sale_price));
                    tv_price_ratio1.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_month.getText().toString().equals("") || edit_price_month.getText().toString() == null) {
                    tv_price_month.setText("가격");
                } else {
                    monthly_price = Long.parseLong(edit_price_month.getText().toString().trim());
                    tv_price_month.setText(translatePrice(monthly_price));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_manage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_manage.getText().toString().equals("") || edit_price_manage.getText().toString() == null) {
                    tv_price_manage.setText("가격");
                } else {
                    admin_expenses = Long.parseLong(edit_price_manage.getText().toString().trim());
                    tv_price_manage.setText(translatePrice(admin_expenses));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        slider_ratio1.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (sale_price == -1) {
                    tv_price_ratio1.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                } else {
                    List<Float> thumbs = slider.getValues();
                    down_pay_per = Math.round(thumbs.get(0));
                    intermediate_pay_per = Math.round(thumbs.get(1));
                    intermediate_pay_per = intermediate_pay_per - down_pay_per;
                    balance_per = 100 - down_pay_per - intermediate_pay_per;
                    tv_ratio1.setText(down_pay_per + "% : " + intermediate_pay_per + "% : " + balance_per + "%");
                    price_first = sale_price * down_pay_per / 100;
                    price_second = sale_price * intermediate_pay_per / 100;
                    price_third = sale_price * balance_per / 100;
                    tv_price_ratio1.setText("계약금 : " + translatePrice(price_first) + "\n중도금 : " + translatePrice(price_second) + "\n    잔금 : " + translatePrice(price_third));
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
            }
        });
        slider_ratio2.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (price_first == -1) {
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                } else {
                    List<Float> thumbs = slider.getValues();
                    provisional_down_pay_per = Math.round(thumbs.get(0));
                    long second = 100 - provisional_down_pay_per;
                    tv_ratio2.setText(provisional_down_pay_per + "% : " + second + "%");
                    long first = price_first * provisional_down_pay_per / 100;
                    second = price_first * second / 100;
                    tv_price_ratio2.setText("        가계약금 : " + translatePrice(first) + "\n나머지 계약금 : " + translatePrice(second));
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_upload:
                onBackPressed();
                break;

            case R.id.btn_type1:
                if (!sale_type.equals("월세")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.VISIBLE);
                    tv_price_type.setText("보증금");
                    sale_type = "월세";
                }
                break;

            case R.id.btn_type2:
                if (!sale_type.equals("전세")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("전세금");
                    sale_type = "전세";
                }
                break;

            case R.id.btn_type3:
                if (!sale_type.equals("매매")) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("매매가");
                    sale_type = "매매";
                }
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


            case R.id.chk_nego:
                if (chk_nego.isChecked()) {
                    nego = true;
                } else {
                    nego = false;
                }
                break;
            case R.id.chk_door:
                if (chk_door.isChecked()) {
                    middle_door = true;
                } else {
                    middle_door = false;
                }
                break;
            case R.id.chk_air:
                if (chk_air.isChecked()) {
                    air_conditioner = true;
                } else {
                    air_conditioner = false;
                }
                break;
            case R.id.chk_ref:
                if (chk_ref.isChecked()) {
                    refrigerator = true;
                } else {
                    refrigerator = false;
                }
                break;
            case R.id.chk_kimchi:
                if (chk_kimchi.isChecked()) {
                    kimchi_refrigerator = true;
                } else {
                    kimchi_refrigerator = false;
                }
                break;
            case R.id.chk_closet:
                if (chk_closet.isChecked()) {
                    closet = true;
                } else {
                    closet = false;
                }
                break;
            case R.id.chk_oven:
                if (chk_oven.isChecked()) {
                    oven = true;
                } else {
                    oven = false;
                }
                break;
            case R.id.chk_induction:
                if (chk_induction.isChecked()) {
                    induction = true;
                } else {
                    induction = false;
                }
                break;
            case R.id.chk_airsystem:
                if (chk_airsystem.isChecked()) {
                    airsystem = true;
                } else {
                    airsystem = false;
                }
                break;

            case R.id.tv_complete:
                upload_dialog(view);
                break;

            case R.id.edit_apartment:
                //사진 uri들을 저장하고 액티비티 전환
                Bundle bundle = new Bundle();
                for (int i = 0; i < models.size(); i++) {
                    Uri uri = models.get(i).getUri();
                    if (uri != null) {
                        bundle.putParcelable("picture" + i, uri);
                    }
                }
                getIntent().putExtras(bundle);
                Intent intent = new Intent(getBaseContext(), SearchActivity_upload.class);
//                intent.putExtra("models",models);
                startActivityForResult(intent, 1);

            case R.id.chk_movenow:
                if (chk_movenow.isChecked()) {
                    tv_movedate.setVisibility(View.GONE);
                    movedate = "즉시 입주";
                } else {
                    tv_movedate.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tv_movedate:
                DatePickerDialog dialog = new DatePickerDialog(UploadActivity.this, callbackMethod, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
        }
    }


    public String translatePrice(Long price) {

        String a = "", b = "", c = "";
        if (price == 0) {
            return "0원";
        }
        if (price == -1) {
            return "무제한";
        }
        if (price >= 100000000) {
            Long price1 = price / 100000000;
            price %= 100000000;
            a = price1 + "억 ";
        }
        if (price >= 10000) {
            Long price2 = price / 10000;
            price %= 10000;
            b = price2 + "만";
        }
        if (price > 0) {
            c = " " + price.toString();
        }
        return a + b + c + "원";
    }

    public String translateArea(Double area) {
        area *= 0.3025;
        area = Math.round(area * 10) / 10.0;
        return area.toString() + " 평";
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_upload, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        Button ok_btn = dialogView.findViewById(R.id.btn_okay_dialog_upload);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                userId = mUser.getUserId();

                boolean judge_pic = true;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getUri() == null) judge_pic = false;
                }
                if (judge_pic) {
                    pictures = new ArrayList<>();
                    getPicturesList();
                    getDescription();
                    mHouse = new House(userId, residence_name, code, address, sido, sigungoo, dongri, date,
                            allnumber, parkingnumber, contact, dong, ho, net_leaseable_area, leaseable_area,
                            residence_type, sale_type, sale_price, monthly_price, admin_expenses, provisional_down_pay_per,
                            down_pay_per, intermediate_pay_per, balance_per, roomcnt, toiletcnt, middle_door,
                            air_conditioner, refrigerator, kimchi_refrigerator, closet, oven, induction, airsystem,
                            nego, short_description, long_description, models_description.get(0), models_description.get(1),
                            models_description.get(2), models_description.get(3), models_description.get(4),
                            models_description.get(5), models_description.get(6), models_description.get(7), models_description.get(8), movedate);
                    doRetrofit();
                } else {
                    Toast.makeText(UploadActivity.this, "필수 사진을 등록해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button cancle_btn = dialogView.findViewById(R.id.btn_no_dialog_upload);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void getDescription() {
        models_description = new ArrayList<>();
        models_description.add(models.get(0).getDescription());
        models_description.add(models.get(1).getDescription());
        models_description.add(models.get(2).getDescription());
        models_description.add(models.get(3).getDescription());

        for (int i = 0; i < roomcnt; i++) {
            models_description.add(models.get(4 + i).getDescription());
        }
        for (int i = 0; i < 3 - roomcnt; i++) {
            models_description.add("");
        }
        for (int i = 0; i < toiletcnt; i++) {
            models_description.add(models.get(4 + roomcnt + i).getDescription());
        }
        for (int i = 0; i < 2 - toiletcnt; i++) {
            models_description.add("");
        }
    }

    private void doRetrofit() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.uploadHouse(
                mHouse.getUserId(),
                mHouse.getResidence_name(),
                mHouse.getCode(),
                mHouse.getAddress(),
                mHouse.getSido(),
                mHouse.getSigungoo(),
                mHouse.getDongri(),
                mHouse.getDate(),
                mHouse.getAllnumber(),
                mHouse.getParkingnumber(),
                mHouse.getContact(),
                mHouse.getDong(),
                mHouse.getHo(),
                mHouse.getNet_leaseable_area(),
                mHouse.getLeaseable_area(),
                mHouse.getResidence_type(),
                mHouse.getSale_type(),
                mHouse.getSale_price(),
                mHouse.getMonthly_price(),
                mHouse.getAdmin_expenses(),
                mHouse.getProvisional_down_pay_per(),
                mHouse.getDown_pay_per(),
                mHouse.getIntermediate_pay_per(),
                mHouse.getBalance_per(),
                mHouse.getRoom_num(),
                mHouse.getToilet_num(),
                mHouse.isMiddle_door(),
                mHouse.isAir_conditioner(),
                mHouse.isRefrigerator(),
                mHouse.isKimchi_refrigerator(),
                mHouse.isCloset(),
                mHouse.isOven(),
                mHouse.isInduction(),
                mHouse.isAirsystem(),
                mHouse.isNego(),
                mHouse.getShort_description(),
                mHouse.getLong_description(),
                mHouse.getApartment_description(),
                mHouse.getPorch_description(),
                mHouse.getLivingroom_description(),
                mHouse.getKitchen_description(),
                mHouse.getRoom1_description(),
                mHouse.getRoom2_description(),
                mHouse.getRoom3_description(),
                mHouse.getToilet1_description(),
                mHouse.getToilet2_description(),
                mHouse.getMovedate(),
                pictures.get(0),
                pictures.get(1),
                pictures.get(2),
                pictures.get(3),
                pictures.get(4),
                pictures.get(5),
                pictures.get(6),
                pictures.get(7),
                pictures.get(8))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("testtest", "");
                        String test_code = response.headers().get("code");
                        String test_body = response.headers().get("code");
                        Log.d("UploadActivity", "headercode" + test_code);
                        Log.d("UploadActivity", "body" + test_body);
                        Log.d("UploadActivity", "pictures" + pictures);


                        if (test_code != null && test_code.equals("00")) {
                            Toast.makeText(UploadActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UploadActivity.this, "업로드 실패" + test_code, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Log.d("UploadActivity", throwable.getMessage());
                    }
                });
    }

    private void getPicturesList() {
        for (int i = 0; i < models.size(); i++) {
            Uri uri = models.get(i).getUri();
            if (uri != null) {
                transUriToMultiPartFile(uri, i);
            }
        }
        for (int i = models.size(); i < 9; i++) {
            transUriToMultiPartFile(null, i);
        }
    }

    private void transUriToMultiPartFile(Uri uri, int position) {
        MultipartBody.Part filePart = null;
        Bitmap img = null;

        //change Uri to Bitmap
        if (uri != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    img = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                } else {
                    img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            img = BitmapFactory.decodeResource(getResources(), R.drawable.icon_parking);
        }

        try {
            String filenameJPEG = "file" + (position + 1) + ".jpg";
            File f = new File(this.getCacheDir(), filenameJPEG);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, fos);
            fos.close();

            String filename = "file" + (position + 1);

            filePart = MultipartBody.Part.createFormData(filename, f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pictures.add(filePart);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    models.get(picture_clicked_position).setUri(imageUri);
                    viewPager.setAdapter(adapter);
                    //mIvPicture.setImageURI(imageUri);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }

            viewPager.setCurrentItem(picture_clicked_position, true);
        }
        if (requestCode == NAME_REQUEST) {
            if (resultCode == RESULT_OK) {
                code = data.getStringExtra("code");
                residence_name = data.getStringExtra("name");
                address = data.getStringExtra("address");
                sido = data.getStringExtra("sido");
                sigungoo = data.getStringExtra("sigungoo");
                dongri = data.getStringExtra("dongri");
                date = data.getStringExtra("date");
                allnumber = data.getIntExtra("allnumber", 0);
                parkingnumber = data.getIntExtra("parkingnumber", 0);
                contact = data.getStringExtra("contact");

                edit_apartment.setText(residence_name);
                tv_apartaddress_load.setText(address);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "아파트 선택 취소", Toast.LENGTH_LONG).show();
            }

            //사진들 uri를 다시 저장시켜주는 과정
            Bundle bundle = getIntent().getExtras();
            for (int i = 0; i < models.size(); i++) {
                Uri uri = bundle.getParcelable("picture" + i);
                if (uri != null) {
                    models.get(i).setUri(uri);
                }
            }

            viewPager.setAdapter(adapter);

            //실거래가를 위한 csv 파싱
            renewlist();
            realPrice();
            ll_realprice.setVisibility(View.VISIBLE);
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
                            chart.setVisibility(View.GONE);
                            ll_realprice_show.setVisibility(View.GONE);
                            ll_realprice_noshow.setVisibility(View.VISIBLE);
                        }
                        else{

                            ll_realprice_show.setVisibility(View.VISIBLE);
                            ll_realprice_noshow.setVisibility(View.GONE);
                            adapter_realprice = new RecyclerViewAdapter_Realprice(getApplicationContext(), arr);
                            adapter_realprice.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter_realprice);

                            int[] cnt = {0, 0, 0, 0, 0, 0, 0, 0};
                            Float[] sum = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
                            for(Realprice realprice : arr) {
                                int month = Integer.parseInt(realprice.getContract_date().substring(4, 6));
                                int price = changePrice(realprice.getSale_price());
                                int area = changeArea(realprice.getNet_leaseable_area());
                                ++cnt[month];
                                sum[month] += price / (area * 0.3024f);
                            }

                            ArrayList<Entry> values = new ArrayList<>();
                            for(int i =1; i <= 7; ++i) {
                                values.add(new Entry(i, sum[i] / cnt[i]));
                            }
                            chart.setVisibility(View.VISIBLE);
                            lineGraph.createChart();
                            lineGraph.setData(values);
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
                if (address.contains(str[6])) {  // 검색어가 포함된 경우
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
        tv_name_realprice.setText(residence_name +" "+ net_leaseable_area+"m²");
        if(arr!=null) {
            for (int i = 0; i < arr.size(); i++) {
                int temp = changeArea(arr.get(i).getNet_leaseable_area());
                if (temp == Integer.parseInt(String.valueOf(Math.round(net_leaseable_area)))) {
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
        int av2 = (int)(3.3*average/Integer.parseInt(String.valueOf(Math.round(net_leaseable_area))));
        tv_price1_realprice.setText(translatePrice2(av));
        tv_price2_realprice.setText(translatePrice2(av2));
    }

    public int changeArea(String area) {
        Double temp = Double.parseDouble(area);
        return Integer.parseInt(String.valueOf(Math.round(temp)));
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

    @Override
    public void onClick(int value) {
        picture_clicked_position = value;
    }

}
