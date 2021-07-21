package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity.java";

    private EditText mEditEmail, mEditPassword;
    private Button mLoginButton, mJoinButton;
    private TextView mResetPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditEmail = findViewById(R.id.edit_login_email);
        mEditPassword = findViewById(R.id.edit_login_password);
        mLoginButton = findViewById(R.id.btn_login);
        mJoinButton = findViewById(R.id.btn_join);
        mResetPassword = findViewById(R.id.reset_password);

        auth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = mEditEmail.getText().toString();
                String txt_password = mEditPassword.getText().toString();

                if(TextUtils.isEmpty(txt_email)){
                    Toast.makeText(LoginActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        loginServer(txt_email, txt_password);
                                    }
                                }
                            });
                }
            }
        });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    private void loginServer(String mEmail, String mPassword) {

        Log.d(TAG, "loginServer");

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.login(mEmail,mPassword).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse");

                String code = response.headers().get("code");

                Toast.makeText(getApplicationContext(),"code = "+response.headers() , Toast.LENGTH_SHORT).show();

                if (code != null && code.equals("00")) {
                    Log.d(TAG, "code.equlas('00')");

                    Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"로그인 실패 response code : " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
