package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
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

                validateUserTask mTask = new validateUserTask();
                mTask.execute(mId,mPassword);

            }
        });
    }

    private class validateUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            JSONObject json = new JSONObject();

            try {
                json.put("id",params[0]);
                json.put("password",params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            try {
//                json.put("password",params[1]);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());

            try {
                RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
                Call<auth> login = mRESTApi.login(body);
                Response res = login.execute();

                if(res.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else{ }

            } catch (IOException e) {
                e.printStackTrace();
            }

            String res = null;
            return res;
        }//close doInBackground

        @Override
        protected void onPostExecute(String result) {

        }//close onPostExecute

    }// close validateUserTask
}
