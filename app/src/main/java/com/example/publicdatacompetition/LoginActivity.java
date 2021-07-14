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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditId, mEditPassword;
    private Button mLoginButton;
    private String mId, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditId = findViewById(R.id.id);
        mEditPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mId = mEditId.getText().toString();
                mPassword = mEditPassword.getText().toString();

//                validateUserTask mTask = new validateUserTask();
//                mTask.execute(mId,mPassword);

                RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
                mRESTApi.login(mId,mPassword).enqueue(new Callback<List<Map<String, Object>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 00) {
                                Log.d("LoginActivity", "login successful !");
                                Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                Log.d("LoginActivity", "login failure !" + response.code());
                                Toast.makeText(getApplicationContext(),"로그인 실패 response code : " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, Object>>> call, Throwable throwable) {
                        Log.d("LoginActivity", "retrofit failure !");
                        Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

//    private class validateUserTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            JSONObject json = new JSONObject();
//
//            try {
//                json.put("id",params[0]);
//                json.put("password",params[1]);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            try {
////                json.put("password",params[1]);
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//
//            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());
//
//            try {
//                RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
//                Call<List<Map<String,Object>>> login = mRESTApi.login(body);
//                Response res = login.execute();
//
//                if(res.isSuccessful()){
//                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(i);
//                } else{ }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String res = null;
//            return res;
//        }//close doInBackground
//
//        @Override
//        protected void onPostExecute(String result) {
//
//        }//close onPostExecute
//
//    }// close validateUserTask
}
