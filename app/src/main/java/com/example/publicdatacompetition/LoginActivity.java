package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    private EditText mEditId, mEditPassword;
    private Button mLoginButton, mJoinButton;
    private String mId, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mEditId = findViewById(R.id.edit_login_id);
        mEditPassword = findViewById(R.id.edit_login_password);
        mLoginButton = findViewById(R.id.btn_login);
        mJoinButton = findViewById(R.id.btn_join);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mId = mEditId.getText().toString();
                mPassword = mEditPassword.getText().toString();

//                validateUserTask mTask = new validateUserTask();
//                mTask.execute(mId,mPassword);

                RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
                mRESTApi.login(mId,mPassword).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String aa = response.headers().get("code");

                        Toast.makeText(getApplicationContext(),"aa = "+response.headers() , Toast.LENGTH_SHORT).show();
                        Log.d("LoginActivity", "통신 성공 !" + response.headers());

//                            if (response.code() == 0) {
//                                Log.d("LoginActivity", "login successful !");
//                                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(i);
//                            } else {
//                                Log.d("LoginActivity", "login failure !" + response.code());
//                                Toast.makeText(getApplicationContext(),"로그인 실패 response code : " + aa, Toast.LENGTH_SHORT).show();
//                            }

                        if (aa != null && aa.equals("00")) {
                            Log.d("LoginActivity", "login successful !");
                            Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.d("LoginActivity", "login failure !" + response.code());
                            Toast.makeText(getApplicationContext(),"로그인 실패 response code : " + aa, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Log.d("LoginActivity", "retrofit failure !"+throwable);
                        Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
