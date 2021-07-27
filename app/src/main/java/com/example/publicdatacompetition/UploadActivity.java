package com.example.publicdatacompetition;

import android.animation.ArgbEvaluator;
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
import android.widget.Adapter;
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
import androidx.viewpager.widget.ViewPager;

import com.example.publicdatacompetition.Adapter.PagerAdapter_Picture;
import com.example.publicdatacompetition.Model.House;
import com.example.publicdatacompetition.Model.Pictures;
import com.example.publicdatacompetition.Model.User;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private AppCompatButton btn_type1, btn_type2, btn_type3, btn_no_dialog_upload, btn_okay_dialog_upload;
    private LinearLayout lin_price_month;
    private TextInputEditText edit_dong, edit_ho, edit_area1, edit_area2, edit_price_all, edit_price_month, edit_price_manage,
            edit_introduce_short, edit_introduce_long, edit_room, edit_toilet, edit_introduce_livingroom, edit_introduce_kitchen,
            edit_introduce_room1, edit_introduce_room2, edit_introduce_room3, edit_introduce_toilet1, edit_introduce_toilet2, edit_introuce_apartment;
    private TextView tv_area1, tv_area2, tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_price_manage, tv_complete, edit_apartment, tv_movedate, tv_apartaddress_load;
    private RangeSlider slider_ratio1, slider_ratio2;
    private CheckBox chk_nego, chk_door, chk_air, chk_ref, chk_kimchi, chk_closet, chk_oven, chk_induction, chk_airsystem, chk_movenow;
    private Calendar cal;

    private long price_first, price_second, price_third;

    private int picture_clicked_position = 0;

    private PagerAdapter_Picture pagerAdapter_picture;

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private ViewPager viewPager;
    private PagerAdapter_Picture adapter;
    private List<Pictures> models;

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
    private String allnumber;//세대수
    private String parkingnumber;//총주차대수
    private String contact;//관리사무소 연락처

    private Integer dong, ho;//동,호수
    private Double net_leaseable_area;//전용면적
    private Double leaseable_area;//공급면적

    private String residence_type;//매물 타입(A,V,O)
    private String sale_type;//"월세","전세","매매"
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private Long admin_expenses;//관리비

    private Integer provisional_down_pay_per;//가계약금 비율
    private Integer down_pay_per;//계약금 비율
    private Integer intermediate_pay_per;//중도금 비율
    private Integer balance_per;//잔금 비율

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        init();

        roomcnt = Integer.parseInt(getIntent().getStringExtra("roomcnt"));
        toiletcnt = Integer.parseInt(getIntent().getStringExtra("toiletcnt"));
        models = new ArrayList<>();
        models.add(new Pictures("아파트 외관 사진을 등록해주세요", R.drawable.image_apartment,"위치, 주변 시설, 아파트 정원 등등 상세하게 적어주세요","아파트 소개"));
        models.add(new Pictures("현관 사진을 등록해주세요", R.drawable.image_frontdoor, "현관 크기, 확장 여부, 신발장 공간 등등 상세하게 적어주세요","현관 소개"));
        models.add(new Pictures("거실 사진을 등록해주세요", R.drawable.image_livingroom, "베란다 확장 여부, 방향, 바닥재질 등등 상세하게 적어주세요","거실 소개"));
        models.add(new Pictures("주방 사진을 등록해주세요", R.drawable.image_kitchen, "수납공간, 식탁 여부, 인테리어 등등 상세하게 적어주세요","주방 소개"));
        for (int i =1; i<=roomcnt; i++) {
            models.add(new Pictures(i + "번 방" + " 사진을 등록해주세요", R.drawable.image_room4, "채광이나 방향, 벽지 상태 등등 상세하게 적어주세요",i+"번 방 소개"));
        }
        for (int i=1; i<=toiletcnt; i++) {
            models.add(new Pictures(i + "번 화장실" + " 사진을 등록해주세요", R.drawable.image_toilet1, "수압, 환풍기 상태, 습식/건식 여부 등등 상세하게 적어주세요",i+"번 화장실 소개"));
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

        cal = Calendar.getInstance();

        btn_back = findViewById(R.id.btn_back_upload);

        btn_type1 = findViewById(R.id.btn_type1);
        btn_type2 = findViewById(R.id.btn_type2);
        btn_type3 = findViewById(R.id.btn_type3);

        lin_price_month = findViewById(R.id.lin_price_month);

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
//        edit_introuce_apartment = findViewById(R.id.edit_introduce_apartment);
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

        btn_back.setOnClickListener(this);
        btn_type1.setOnClickListener(this);
        btn_type2.setOnClickListener(this);
        btn_type3.setOnClickListener(this);
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

        residence_type = "A";
        sale_type = "월세";

        nego = false;
        air_conditioner = false;
        middle_door = false;
        refrigerator = false;
        kimchi_refrigerator = false;
        closet = false;

        price_first = -1;
        sale_price = -1L;

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                movedate = year + "년 " + month + "월 " + dayOfMonth + "일";
                tv_movedate.setText(movedate);
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

//        edit_room.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_room.getText().toString().equals("") || edit_room.getText().toString() == null) {
//
//                } else {
//                    room_num = Integer.parseInt(edit_room.getText().toString());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_toilet.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_toilet.getText().toString().equals("") || edit_toilet.getText().toString() == null) {
//
//                } else {
//                    toilet_num = Integer.parseInt(edit_toilet.getText().toString());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

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

//        edit_introuce_apartment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introuce_apartment.getText().toString().equals("") || edit_introuce_apartment.getText().toString() == null) {
//
//                } else {
//                    apartment_description = edit_introuce_apartment.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_livingroom.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_livingroom.getText().toString().equals("") || edit_introduce_livingroom.getText().toString() == null) {
//
//                } else {
//                    livingroom_description = edit_introduce_livingroom.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_kitchen.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_kitchen.getText().toString().equals("") || edit_introduce_kitchen.getText().toString() == null) {
//
//                } else {
//                    kitchen_description = edit_introduce_kitchen.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_room1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_room1.getText().toString().equals("") || edit_introduce_room1.getText().toString() == null) {
//
//                } else {
//                    room1_description = edit_introduce_room1.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_room2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_room2.getText().toString().equals("") || edit_introduce_room2.getText().toString() == null) {
//
//                } else {
//                    room2_description = edit_introduce_room2.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_room3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_room3.getText().toString().equals("") || edit_introduce_room3.getText().toString() == null) {
//
//                } else {
//                    room3_description = edit_introduce_room3.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_toilet1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_toilet1.getText().toString().equals("") || edit_introduce_toilet1.getText().toString() == null) {
//
//                } else {
//                    toilet1_description = edit_introduce_toilet1.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        edit_introduce_toilet2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (edit_introduce_toilet2.getText().toString().equals("") || edit_introduce_toilet2.getText().toString() == null) {
//
//                } else {
//                    toilet2_description = edit_introduce_toilet2.getText().toString();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

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
                Intent intent = new Intent(getBaseContext(), SearchActivity_upload.class);
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
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_dialog_upload);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                userId = ((User) getApplication()).getId();
                // todo : user 객채 만들어서 불러와야함

                boolean judge_pic = models.get(0).getUri() != null && models.get(1).getUri() != null && models.get(2).getUri() != null && models.get(3).getUri() != null;
                if (judge_pic) {
                    pictures = new ArrayList<>();
                    getPicturesList();
                    mHouse = new House(userId, residence_name, address, sido, sigungoo, dongri, date, allnumber, parkingnumber, contact, code, dong, ho, net_leaseable_area, leaseable_area, residence_type, sale_type, sale_price, monthly_price, admin_expenses, provisional_down_pay_per, down_pay_per, intermediate_pay_per, balance_per, room_num, toilet_num, middle_door, air_conditioner, refrigerator, kimchi_refrigerator, closet, oven, induction, airsystem, nego, short_description, long_description, models.get(0).getDescription(),  models.get(2).getDescription(),  models.get(3).getDescription(),  models.get(4).getDescription(),  models.get(5).getDescription(),  models.get(6).getDescription(),  models.get(5+roomcnt).getDescription(),  models.get(6+roomcnt).getDescription(),  movedate);
                    doRetrofit();
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(UploadActivity.this, "필수 사진을 등록해주세요", Toast.LENGTH_SHORT).show();
                }

                // todo : retrofit

//                Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                startActivityForResult(intent,0);
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

    private void doRetrofit() {

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        Log.d("beforeUploadActivity", "pictures" + pictures);
        mRESTApi.uploadHouse(mHouse.getUserId(), mHouse.getResidence_name(), mHouse.getCode(), mHouse.getAddress(), mHouse.getSido(), mHouse.getSigungoo(), mHouse.getDongri(), mHouse.getDate(), mHouse.getAllnumber(), mHouse.getParkingnumber(), mHouse.getContact(), mHouse.getDong(), mHouse.getHo(), mHouse.getNet_leaseable_area(), mHouse.getLeaseable_area(), mHouse.getResidence_type(), mHouse.getSale_type(), mHouse.getSale_price(), mHouse.getMonthly_price(), mHouse.getAdmin_expenses(), mHouse.getProvisional_down_pay_per(), mHouse.getDown_pay_per(), mHouse.getIntermediate_pay_per(), mHouse.getBalance_per(), mHouse.getRoom_num(), mHouse.getToilet_num(), mHouse.isMiddle_door(), mHouse.isAir_conditioner(), mHouse.isRefrigerator(), mHouse.isKimchi_refrigerator(), mHouse.isCloset(), mHouse.isOven(), mHouse.isInduction(), mHouse.isAirsystem(), mHouse.isNego(), mHouse.getShort_description(), mHouse.getLong_description(), mHouse.getApartment_description(), mHouse.getLivingroom_description(), mHouse.getKitchen_description(), mHouse.getRoom1_description(), mHouse.getRoom2_description(), mHouse.getRoom3_description(), mHouse.getToilet1_description(), mHouse.getToilet2_description(), mHouse.getMovedate(), pictures)
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
            img = BitmapFactory.decodeResource(getResources(), R.drawable.preview);
        }

        try {

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //create a file to write bitmap data
            File f = new File(this.getCacheDir(), "filename");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            filePart = MultipartBody.Part.createFormData("file",
                    f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
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
                allnumber = data.getStringExtra("allnumber");
                parkingnumber = data.getStringExtra("parkingnumber");
                contact = data.getStringExtra("contact");


                edit_apartment.setText(residence_name);
                tv_apartaddress_load.setText(address);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "아파트 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(int value) {
        picture_clicked_position = value;
    }

}
