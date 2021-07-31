package com.example.publicdatacompetition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.publicdatacompetition.Model.Contract;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

public class ShowContractActivity extends AppCompatActivity {

    Contract mContract;

    private AppCompatButton btn_upload;
    private LinearLayout lin_price_month;
    private TextView tv_address_apartment, tv_purpose, tv_area, tv_price1, tv_price2, tv_price_type, tv_provisional_down_pay, tv_down_pay, tv_intermediate_pay, tv_balance, tv_date, tv_name1, tv_birth1, tv_phonenumber1, tv_name2, tv_birth2, tv_phonenumber2, tv_special;

    private String type;//전세/매매/월세 타입
    private String address_apartment;//도로명 주소 + 아파트 이름
    private String purpose;//아파트 용도
    private String area;//전용면적/공급면적

    // String으로 10000000원(1천만원)으로 형식 맞춤
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
    private String id2;//매수자 아이디

    private Boolean editable;//수정가능여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract1);

        getContract();
        init();
    }

    private void init() {

        btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setText("가계약서 동의하기");
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_contract_2, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button ok_btn = dialogView.findViewById(R.id.btn_okay_contract);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo 서버와의 통신 부분
                        //editable만 수정해서 보내면 ok입니다
                        editable = false;
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
        });

        lin_price_month = findViewById(R.id.lin_price_month);
        tv_price_type = findViewById(R.id.tv_price_type);
        if (type.equals("전세")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("전세금");
        }
        if (type.equals("월세")) {
            lin_price_month.setVisibility(View.VISIBLE);
            tv_price_type.setText("보증금");
        }
        if (type.equals("매매")) {
            lin_price_month.setVisibility(View.GONE);
            tv_price_type.setText("매매금");
        }

        tv_address_apartment = findViewById(R.id.tv_address_apartment);
        tv_address_apartment.setText(address_apartment);
        tv_purpose = findViewById(R.id.tv_purpose);
        tv_purpose.setText(purpose);
        tv_area = findViewById(R.id.tv_area);
        tv_area.setText(area);
        tv_price1 = findViewById(R.id.tv_price1);
        tv_price1.setText(sale_prices);
        tv_price2 = findViewById(R.id.tv_price2);
        tv_price2.setText(monthly_prices);
        tv_provisional_down_pay = findViewById(R.id.tv_provisional_down_pay);
        tv_provisional_down_pay.setText(provisional_down_pay);
        tv_down_pay = findViewById(R.id.tv_down_pay);
        tv_down_pay.setText(down_pay);
        tv_intermediate_pay = findViewById(R.id.tv_intermediate_pay);
        tv_intermediate_pay.setText(intermediate_pay);
        tv_balance = findViewById(R.id.tv_balance);
        tv_balance.setText(balance);
        tv_special = findViewById(R.id.tv_special);
        tv_special.setText(special);
        tv_date = findViewById(R.id.tv_date);
        tv_date.setText(date);
        tv_name1 = findViewById(R.id.tv_name1);
        tv_name1.setText(name1);
        tv_birth1 = findViewById(R.id.tv_birth1);
        tv_birth1.setText(birth1);
        tv_phonenumber1 = findViewById(R.id.tv_phonenumber1);
        tv_phonenumber1.setText(phonenumber1);
        tv_name2 = findViewById(R.id.tv_name2);
        tv_name2.setText(name2);
        tv_birth2 = findViewById(R.id.tv_birth2);
        tv_birth2.setText(birth2);
        tv_phonenumber2 = findViewById(R.id.tv_phonenumber2);
        tv_phonenumber2.setText(phonenumber2);
    }

    private void getContract() {
        //todo 서버로부터 mContact 가져옴!
        type = mContract.getType();
        address_apartment = mContract.getAddress_apartment();
        purpose = mContract.getPurpose();
        area = mContract.getArea();
        date = mContract.getDate();
        sale_prices = mContract.getSale_prices();
        monthly_prices = mContract.getMonthly_prices();
        provisional_down_pay = mContract.getProvisional_down_pay();
        down_pay = mContract.getDown_pay();
        intermediate_pay = mContract.getIntermediate_pay();
        balance = mContract.getBalance();
        name1 = mContract.getName1();
        name2 = mContract.getName2();
        birth1 = mContract.getBirth1();
        birth2 = mContract.getBirth2();
        phonenumber1 = mContract.getPhonenumber1();
        phonenumber2 = mContract.getPhonenumber2();
        id2 = mContract.getId2();
    }
}