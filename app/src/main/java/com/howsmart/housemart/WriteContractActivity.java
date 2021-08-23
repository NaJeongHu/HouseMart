package com.howsmart.housemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.User;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteContractActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "MakeContractActivity";

    UploadActivity mUploadActivity;

    private ImageView btn_back;
    private AppCompatButton btn_upload;
    private LinearLayout lin_price_month;
    private TextInputEditText edit_price_all, edit_price_month, edit_purpose, edit_special;
    private TextView tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_address_apartment, tv_purpose, tv_area, tv_price1, tv_price2, tv_provisional_down_pay, tv_down_pay, tv_intermediate_pay, tv_balance, tv_date, tv_name1, tv_birth1, tv_phonenumber1, tv_name2, tv_birth2, tv_phonenumber2, tv_special;
    private RangeSlider slider_ratio1, slider_ratio2;

    //계약서에 쓰이는 변수
    private String sale_type;//전세/매매/월세 타입
    private String address_apartment;//도로명 주소 + 아파트 이름
    private String purpose;//아파트 용도
    private String area;//전용면적/공급면적
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private int provisional_down_pay_per;//가계약금 비율
    private int down_pay_per;//계약금 비율
    private int intermediate_pay_per;//중도금 비율
    private int balance_per;//잔금 비율

    private String down_pay;//계약금
    private String provisional_down_pay;//가계약금
    private String intermediate_pay;//중도금
    private String balance;//잔금

    private String special;//특약 사항
    private String date;//오늘 날짜

    private Long id1, id2; //매도인, 매수인 아이디
    private String name1, name2;//매도인,매수인 이름
    private String birth1, birth2;//매도인,매수인 생년월일
    private String phonenumber1, phonenumber2;//매도인,매수인 전화번호

    private Boolean editable;//수정가능여부

    private long price_first, price_second, price_third;
    
    private  RESTApi mRESTApi;

    private Long houseIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_upload);

        init();
        getDataFromServer();
    }

    private void getDataFromServer() {

        Intent intent = getIntent();
        phonenumber1 = intent.getStringExtra("sellerPhone");
        phonenumber2 = intent.getStringExtra("buyerPhone");
        houseIdx = intent.getLongExtra("houseIdx", -1);

        //예외처리
        if(phonenumber1 == null || phonenumber2 == null){
            Log.d(TAG, "Intent에 매도자나 매수자 정보가 없습니다");
            finish();
        } else if(houseIdx == -1){
            Log.d(TAG, "Intent에 매물 idx가 없습니다");
            finish();
        }

        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getContractHouseInfo(houseIdx).enqueue(new Callback<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                String houseCode = response.headers().get("code");

                if(houseCode != null && houseCode.equals("00")) {
                    House house = (House) response.body();
                    if(houseIdx != house.getIdx()) {
                        Log.e(TAG, "서버에서 잘못된 매물을 가져옴");
                        finish();
                    }

                    Log.d(TAG, "매몰: " + house.toString());

                    sale_type = house.getSale_type();
                    address_apartment = house.getAddress() + " " + house.getResidence_name() + " "
                            + house.getDong() + "동 " + house.getHo() + "호";//도로명 주소 + 아파트 이름 + 동 + 호
                    area = house.getNet_leaseable_area() + "m²/" + house.getLeaseable_area() + "m²";// 전용면적/공급면적
                    sale_price = house.getSale_price();
                    monthly_price = house.getMonthly_price();
                    provisional_down_pay_per = house.getProvisional_down_pay_per();
                    down_pay_per = house.getDown_pay_per();
                    intermediate_pay_per = house.getIntermediate_pay_per();
                    balance_per = house.getBalance_per();

                    mRESTApi.getContractUserInfo(phonenumber1).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            String user1Code = response.headers().get("code");

                            if(user1Code != null && user1Code.equals("00")) {
                                User user = (User) response.body();

                                id1 = user.getId();
                                name1 = user.getName();
                                birth1 = user.getIdNum();

                                Log.d(TAG, "매도자: " + user.toString());

                                mRESTApi.getContractUserInfo(phonenumber2).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        String user2Code = response.headers().get("code");

                                        if (user2Code != null && user2Code.equals("00")) {
                                            User user = (User) response.body();

                                            id2 = user.getId();
                                            name2 = user.getName();
                                            birth2 = user.getIdNum();

                                            Log.d(TAG, "매수자: " + user.toString());

                                            init_variable();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable throwable) {
                                        Log.e(TAG, "failure getBuyerInfo", throwable);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.e(TAG, "failure getSellerInfo", throwable);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<House> call, Throwable throwable) {
                Log.e(TAG, "failure getHouseInfo",throwable);
            }
        });
    }

    private void init_variable() {

        Log.d(TAG, "init_variable()...");

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat date2 = new SimpleDateFormat("MM");
        SimpleDateFormat date3 = new SimpleDateFormat("dd");
        date = date1.format(mDate) + "년 " + date2.format(mDate) + "월 " + date3.format(mDate) + "일";
        editable =true;

        tv_date.setText(date);
        if (sale_type.equals("매매")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("매매금");
        } else if (sale_type.equals("전세")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("전세금");
        } else {
            lin_price_month.setVisibility(View.VISIBLE);
            tv_price_type.setText("보증금");
        }
        tv_address_apartment.setText(address_apartment);
        tv_area.setText(area);

        tv_price1.setText(sale_price + "원(" + mUploadActivity.translatePrice(sale_price) + ")");

        if (sale_type.equals("월세")) {
            tv_price2.setText(monthly_price + "원(" + mUploadActivity.translatePrice(monthly_price) + ")");
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
        tv_provisional_down_pay.setText(provisional_down_pay);
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
                    tv_price1.setText(sale_price + "원(" + mUploadActivity.translatePrice(sale_price) + ")");
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
                    tv_price2.setText(monthly_price + "원(" + mUploadActivity.translatePrice(monthly_price) + ")");
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
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_contract);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버와의 통신 부분
                mRESTApi.writeContract(houseIdx, sale_type, address_apartment, purpose, area,
                        sale_price, monthly_price, provisional_down_pay, down_pay, intermediate_pay, balance,
                        special, date, id1, id2).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        //get idx of written contract
                        String code = response.headers().get("code");

                        if(code.equals("00")) {
                            Long contractIdx = response.body();

                            Toast.makeText(WriteContractActivity.this, "계약서를 저장했습니다", Toast.LENGTH_SHORT).show();

                            //send contract idx to ChatActivity
                            Intent intent = new Intent();
                            intent.putExtra("contractIdx", contractIdx);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable throwable) {
                        Toast.makeText(WriteContractActivity.this, "서버와의 연결이 원활하지 않아 계약서를 저장하지 못했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
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