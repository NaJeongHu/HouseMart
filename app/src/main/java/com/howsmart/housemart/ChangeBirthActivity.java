package com.howsmart.housemart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeBirthActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_back_birth;
    private EditText edit_birth;
    private Button btn_change_birth;
    private String birth;

    private String mUserId;

    RESTApi mRESTApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_birth);
        init();
    }

    private void init() {
        mUserId = getIntent().getStringExtra("userId");

        btn_back_birth = findViewById(R.id.btn_back_birth);
        edit_birth = findViewById(R.id.edit_birth);
        btn_change_birth = findViewById(R.id.btn_change_birth);

        btn_back_birth.setOnClickListener(this);
        btn_change_birth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_birth:
                onBackPressed();
                break;

            case R.id.btn_change_birth:
                if (edit_birth.getText().toString().length() == 0) {
                    Toast.makeText(ChangeBirthActivity.this, "변경하실 생년월일을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    birth = edit_birth.getText().toString();
                    doRetrofit();
                }
        }
    }

    private void doRetrofit() {
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        Log.d("testtest", "");
        mRESTApi.changePrivateinfo(mUserId, "birth", null, birth, null, null, null).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String test_code = response.headers().get("code");

                if (test_code != null && test_code.equals("00")) {
                    finish();
                } else {
                    Toast.makeText(ChangeBirthActivity.this, "생년월일 변경 실패" + test_code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("ChangeBirthActivity", "failure change nickname", throwable);
            }

        });
    }
}