package com.howsmart.housemart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.howsmart.housemart.Model.Contract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowContractActivity extends AppCompatActivity {

    private final static String TAG = "ShowContractActivity";

    Contract mContract;

    private AppCompatButton btn_upload;
    private View view_month;
    private TableRow row_month;
    private TextView tv_address_apartment, tv_purpose, tv_area, tv_price1, tv_price2, tv_price_type, tv_provisional_down_pay, tv_down_pay, tv_intermediate_pay, tv_balance, tv_date, tv_name1, tv_birth1, tv_phonenumber1, tv_name2, tv_birth2, tv_phonenumber2, tv_special;

    private String sale_type;//전세/매매/월세 타입
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
    private Long id2;//매수자 아이디

    private Boolean editable;//수정가능여부

    private RESTApi mRESTApi;

    Long contractIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcontract);

        mRESTApi = RESTApi.retrofit.create(RESTApi.class);

        //getContract()가 완료되기 전에 init()에서 sale_type을 참조해서 문제가 생김
        //=> getContract()를 완료한 후 init() 시행하도록 함
        getContract();
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
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.show();

                Button ok_btn = dialogView.findViewById(R.id.btn_okay_contract);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRESTApi.successContract(contractIdx).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                Log.d(TAG, "onResponse...");

                                String code = response.headers().get("code");
                                Boolean body = response.body();

                                if(code.equals("00")){
                                    Toast.makeText(ShowContractActivity.this, "가계약서에 동의했습니다", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable throwable) {
                                Toast.makeText(ShowContractActivity.this, "서버와의 연결이 원활하지 않습니다", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onFailure...", throwable);
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
        });

        view_month = findViewById(R.id.view_month);
        row_month = findViewById(R.id.row_month);
        tv_price_type = findViewById(R.id.tv_price_type);
        if (sale_type.equals("전세")) {
            view_month.setVisibility(View.GONE);
            row_month.setVisibility(View.GONE);
            tv_price_type.setText("전세금");
        }
        if (sale_type.equals("월세")) {
            view_month.setVisibility(View.VISIBLE);
            row_month.setVisibility(View.VISIBLE);
            tv_price_type.setText("보증금");
        }
        if (sale_type.equals("매매")) {
            view_month.setVisibility(View.GONE);
            row_month.setVisibility(View.GONE);
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

        Intent intent = getIntent();
        contractIdx = intent.getLongExtra("contractIdx", -1);

        if(contractIdx == -1) {
            Log.d(TAG, "Intent에 contractIdx가 없음");
            finish();
        }

        mRESTApi.getContract(contractIdx).enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                String code = response.headers().get("code");
                if(code.equals("00")) {
                    mContract = (Contract) response.body();

                    sale_type = mContract.getSale_type();
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
                    special = mContract.getSpecial();
                    name1 = mContract.getName1();
                    name2 = mContract.getName2();
                    birth1 = mContract.getBirth1();
                    birth2 = mContract.getBirth2();
                    phonenumber1 = mContract.getPhonenumber1();
                    phonenumber2 = mContract.getPhonenumber2();
                    id2 = mContract.getId2();

                    editable = mContract.getEditable();

                    init();
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable throwable) {
                Toast.makeText(ShowContractActivity.this, "가계약서 정보를 가져오지 못함", Toast.LENGTH_SHORT).show();
            }
        });
    }
}