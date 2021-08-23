package com.howsmart.housemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.User;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_back_password;
    private EditText edit_password1,edit_password2,edit_password3;
    private Button btn_change_password;

    private String mUserId;
    private String password1,password2,password3;

    RESTApi mRESTApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        mUserId = getIntent().getStringExtra("userId");

        btn_back_password = findViewById(R.id.btn_back_password);
        edit_password1 = findViewById(R.id.edit_password1);
        edit_password2 = findViewById(R.id.edit_password2);
        edit_password3 = findViewById(R.id.edit_password3);
        btn_change_password = findViewById(R.id.btn_change_password);

        btn_back_password.setOnClickListener(this);
        btn_change_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_password:
                onBackPressed();
                break;

            case R.id.btn_change_password:
                if (edit_password2.getText().toString().length() == 0) {
                    Toast.makeText(ChangePasswordActivity.this, "변경하실 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(edit_password2.getText().toString().length()< 8 || edit_password2.getText().toString().length() >20){
                    Toast.makeText(ChangePasswordActivity.this, "비밀번호는 영문, 숫자를 포함하여 8~20자로 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(!edit_password2.getText().toString().equals(edit_password3.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this, "새 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    password1 = edit_password1.getText().toString();
                    password2 = edit_password2.getText().toString();
                    password3 = edit_password3.getText().toString();
                    doRetrofit();
                }
        }
    }

    private void doRetrofit() {
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        Log.d("testtest", "");
        mRESTApi.changePrivateinfo(mUserId, "pass", null, null, password1, password2, password3).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String test_code = response.headers().get("code");

                if (test_code != null && test_code.equals("00")) {
                    firebaseChangePassword();
                    Toast.makeText(ChangePasswordActivity.this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                }
                else if(test_code.equals("12")){
                    Toast.makeText(ChangePasswordActivity.this, "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "닉네임 변경 실패 코드:" + test_code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("ChangeNicknameActivity", "failure change nickname", throwable);
            }

        });
    }

    private void firebaseChangePassword() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updatePassword(password3);
    }
}