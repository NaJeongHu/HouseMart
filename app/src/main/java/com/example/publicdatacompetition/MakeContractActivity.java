package com.example.publicdatacompetition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicdatacompetition.Model.House;
import com.example.publicdatacompetition.Model.User;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeContractActivity extends AppCompatActivity implements View.OnClickListener {

    UploadActivity mUploadActivity;

    private ImageView btn_back;
    private AppCompatButton btn_upload;
    private LinearLayout lin_price_month;
    private TextInputEditText edit_price_all, edit_price_month, edit_purpose, edit_special;
    private TextView tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_address_apartment, tv_purpose, tv_area, tv_price1, tv_price2, tv_provisional_down_pay, tv_down_pay, tv_intermediate_pay, tv_balance, tv_date, tv_name1, tv_birth1, tv_phonenumber1, tv_name2, tv_birth2, tv_phonenumber2, tv_special;
    private RangeSlider slider_ratio1, slider_ratio2;

    //계약서에 쓰이는 변수
    private String type;//전세/매매/월세 타입
    private String address_apartment;//도로명 주소 + 아파트 이름
    private String purpose;//아파트 용도
    private String area;//전용면적/공급면적
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private int provisional_down_pay_per;//가계약금 비율
    private int down_pay_per;//계약금 비율
    private int intermediate_pay_per;//중도금 비율
    private int balance_per;//잔금 비율

    // String으로 10000000원(1천만원) 이런 format으로 형식 맞춤
    private String sale_prices;//매매가/전세금/보증금
    private String monthly_prices;//월세
    private String provisional_down_pay;//가계약금
    private String down_pay;//계약금
    private String intermediate_pay;//중도금
    private String balance;//잔금

    private String special;//특약 사항
    private String date;//오늘 날짜
    private String name1, name2;//매수인,매도인의 이름
    private String birth1, birth2;//생년월일
    private String phonenumber1, phonenumber2;//전화번호

    private long price_first, price_second, price_third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_upload);
        init();
        init_variable();
    }

    private void init_variable() {
        //통신 더미 데이터
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat date2 = new SimpleDateFormat("MM");
        SimpleDateFormat date3 = new SimpleDateFormat("dd");
        date = date1.format(mDate) + "년 " + date2.format(mDate) + "월 " + date3.format(mDate) + "일";
        type = "매매";
        address_apartment = "대구 북구 대현남로 25 대현휴먼시아 205동 1601호";//도로명 주소 + 아파트 이름 + 동 + 호
        area = "105m²/134m²";// 전용면적/공급면적
        sale_price = 1000000000L;
        monthly_price = 150000L;
        provisional_down_pay_per = 10;
        down_pay_per = 10;
        intermediate_pay_per = 40;
        balance_per = 50;
        name1 = "권세진";
        name2 = "나정후";
        birth1 = "970613";
        birth2 = "950729";
        phonenumber1 = "01077395570";
        phonenumber2 = "01012345678";


        tv_date.setText(date);
        if (type.equals("매매")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("매매금");
        } else if (type.equals("전세")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("전세금");
        } else {
            lin_price_month.setVisibility(View.VISIBLE);
            tv_price_type.setText("보증금");
        }
        tv_address_apartment.setText(address_apartment);
        tv_area.setText(area);

        sale_prices = sale_price + "원(" + mUploadActivity.translatePrice(sale_price) + ")";
        tv_price1.setText(sale_prices);

        tv_price1.setText(sale_prices);
        if (type.equals("월세")) {
            monthly_prices = monthly_price + "원(" + mUploadActivity.translatePrice(monthly_price) + ")";
            tv_price2.setText(monthly_prices);
        }

        price_first = sale_price * down_pay_per / 100;
        price_second = sale_price * intermediate_pay_per / 100;
        price_third = sale_price * balance_per / 100;

        down_pay = price_first + "원(" + mUploadActivity.translatePrice(price_first) + ")";
        intermediate_pay = price_second + "원(" + mUploadActivity.translatePrice(price_second) + ")";
        balance = price_third + "원(" + mUploadActivity.translatePrice(price_third) + ")";
        tv_down_pay.setText(down_pay);
        tv_intermediate_pay.setText(intermediate_pay);
        tv_balance.setText(balance);
        long second = 100 - provisional_down_pay_per;
        long first = price_first * provisional_down_pay_per / 100;
        tv_ratio2.setText(provisional_down_pay_per + "% : " + second + "%");
        second = price_first * second / 100;
        tv_ratio1.setText(down_pay_per + "% : " + intermediate_pay_per + "% : " + balance_per + "%");
        tv_price_ratio1.setText("계약금 : " + mUploadActivity.translatePrice(price_first) + "\n중도금 : " + mUploadActivity.translatePrice(price_second) + "\n    잔금 : " + mUploadActivity.translatePrice(price_third));
        tv_price_ratio2.setText("        가계약금 : " + mUploadActivity.translatePrice(first) + "\n나머지 계약금 : " + mUploadActivity.translatePrice(second));
        tv_name1.setText(name1);
        tv_name2.setText(name2);
        tv_birth1.setText(birth1);
        tv_birth2.setText(birth2);
        tv_phonenumber1.setText(phonenumber1);
        tv_phonenumber2.setText(phonenumber2);
    }

    private void init() {

        mUploadActivity = new UploadActivity();

        btn_back = findViewById(R.id.btn_back);
        btn_upload = findViewById(R.id.btn_upload);
        btn_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

        lin_price_month = findViewById(R.id.lin_price_month);

        edit_price_all = findViewById(R.id.edit_price_all);
        edit_price_month = findViewById(R.id.edit_price_month);
        edit_purpose = findViewById(R.id.edit_purpose);
        edit_special = findViewById(R.id.edit_special);

        tv_ratio1 = findViewById(R.id.tv_ratio1);
        tv_ratio2 = findViewById(R.id.tv_ratio2);
        tv_price_ratio1 = findViewById(R.id.tv_price_ratio1);
        tv_price_ratio2 = findViewById(R.id.tv_price_ratio2);
        tv_price_type = findViewById(R.id.tv_price_type);
        tv_price_all = findViewById(R.id.tv_price_all);
        tv_price_month = findViewById(R.id.tv_price_month);

        tv_address_apartment = findViewById(R.id.tv_address_apartment);
        tv_purpose = findViewById(R.id.tv_purpose);
        tv_area = findViewById(R.id.tv_area);
        tv_price1 = findViewById(R.id.tv_price1);
        tv_price2 = findViewById(R.id.tv_price2);
        tv_provisional_down_pay = findViewById(R.id.tv_provisional_down_pay);
        tv_down_pay = findViewById(R.id.tv_down_pay);
        tv_intermediate_pay = findViewById(R.id.tv_intermediate_pay);
        tv_balance = findViewById(R.id.tv_balance);
        tv_special = findViewById(R.id.tv_special);
        tv_date = findViewById(R.id.tv_date);
        tv_name1 = findViewById(R.id.tv_name1);
        tv_birth1 = findViewById(R.id.tv_birth1);
        tv_phonenumber1 = findViewById(R.id.tv_phonenumber1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_birth2 = findViewById(R.id.tv_birth2);
        tv_phonenumber2 = findViewById(R.id.tv_phonenumber2);


        slider_ratio1 = findViewById(R.id.slider_ratio1);
        slider_ratio2 = findViewById(R.id.slider_ratio2);

        btn_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);

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
                    tv_price_all.setText(mUploadActivity.translatePrice(sale_price));
                    sale_prices = sale_price + "원(" + mUploadActivity.translatePrice(sale_price) + ")";
                    tv_price1.setText(sale_prices);
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
                    tv_price_month.setText(mUploadActivity.translatePrice(monthly_price));
                    monthly_prices = monthly_price + "원(" + mUploadActivity.translatePrice(monthly_price) + ")";
                    tv_price2.setText(monthly_prices);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_purpose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_purpose.getText().toString().equals("") || edit_purpose.getText().toString() == null) {
                    tv_purpose.setText("");
                } else {
                    purpose = edit_purpose.getText().toString();
                    tv_purpose.setText(purpose);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_special.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_special.getText().toString().equals("") || edit_special.getText().toString() == null) {
                    tv_special.setText("");
                } else {
                    special = edit_special.getText().toString();
                    tv_special.setText(special);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        slider_ratio1.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> thumbs = slider.getValues();
                down_pay_per = Math.round(thumbs.get(0));
                intermediate_pay_per = Math.round(thumbs.get(1));
                intermediate_pay_per = intermediate_pay_per - down_pay_per;
                balance_per = 100 - down_pay_per - intermediate_pay_per;
                tv_ratio1.setText(down_pay_per + "% : " + intermediate_pay_per + "% : " + balance_per + "%");

                price_first = sale_price * down_pay_per / 100;
                price_second = sale_price * intermediate_pay_per / 100;
                price_third = sale_price * balance_per / 100;

                down_pay = price_first + "원(" + mUploadActivity.translatePrice(price_first) + ")";
                intermediate_pay = price_second + "원(" + mUploadActivity.translatePrice(price_second) + ")";
                balance = price_third + "원(" + mUploadActivity.translatePrice(price_third) + ")";
                tv_down_pay.setText(down_pay);
                tv_intermediate_pay.setText(intermediate_pay);
                tv_balance.setText(balance);

                tv_price_ratio1.setText("계약금 : " + mUploadActivity.translatePrice(price_first) + "\n중도금 : " + mUploadActivity.translatePrice(price_second) + "\n    잔금 : " + mUploadActivity.translatePrice(price_third));
                tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
            }
        });

        slider_ratio2.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> thumbs = slider.getValues();
                provisional_down_pay_per = Math.round(thumbs.get(0));
                long second = 100 - provisional_down_pay_per;
                tv_ratio2.setText(provisional_down_pay_per + "% : " + second + "%");
                long first = price_first * provisional_down_pay_per / 100;
                second = price_first * second / 100;
                provisional_down_pay = first + "원(" + mUploadActivity.translatePrice(first) + ")";
                tv_provisional_down_pay.setText(provisional_down_pay);
                tv_price_ratio2.setText("        가계약금 : " + mUploadActivity.translatePrice(first) + "\n나머지 계약금 : " + mUploadActivity.translatePrice(second));
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.btn_upload:
                upload_dialog(v);
                break;
        }
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_contract, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_contract);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button cancle_btn = dialogView.findViewById(R.id.btn_no_contract);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}