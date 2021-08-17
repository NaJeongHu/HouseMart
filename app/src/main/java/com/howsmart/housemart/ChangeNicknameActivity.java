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

public class ChangeNicknameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_back_nickname;
    private EditText edit_nickname;
    private Button btn_change_nickname;
    private String nickname;

    private String mUserId;

    RESTApi mRESTApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        init();
    }

    private void init() {
        mUserId = getIntent().getStringExtra("userId");

        btn_back_nickname = findViewById(R.id.btn_back_nickname);
        edit_nickname = findViewById(R.id.edit_nickname);
        btn_change_nickname = findViewById(R.id.btn_change_nickname);

        btn_back_nickname.setOnClickListener(this);
        btn_change_nickname.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_nickname:
                onBackPressed();
                break;

            case R.id.btn_change_nickname:
                if (edit_nickname.getText().toString().length() == 0) {
                    Toast.makeText(ChangeNicknameActivity.this, "변경하실 닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    nickname = edit_nickname.getText().toString();
                    doRetrofit();
                }
        }
    }

    private void doRetrofit() {
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        Log.d("testtest", "");
        mRESTApi.changePrivateinfo(mUserId, "nick", nickname, null, null, null, null).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String test_code = response.headers().get("code");

                if (test_code != null && test_code.equals("00")) {
                    finish();
                } else {
                    Toast.makeText(ChangeNicknameActivity.this, "닉네임 변경 실패" + test_code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("ChangeNicknameActivity", "failure change nickname", throwable);
            }

        });
    }
}