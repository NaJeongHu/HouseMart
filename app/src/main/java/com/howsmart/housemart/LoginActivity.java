package com.howsmart.housemart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.howsmart.housemart.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity.java";

    private EditText mEditEmail, mEditPassword;
    private CardView mLoginButton, cardview_login_underinfo;
    private TextView mResetPassword, mJoinButton;

    private FirebaseAuth auth;
    private User user;

    private AlertDialog alertDialog;
    private LottieAnimationView animationView;
    private Animation startAnim, endAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditEmail = findViewById(R.id.edit_login_email);
//        mEditEmail.setBackground(R.color.me);
        mEditPassword = findViewById(R.id.edit_login_password);
        mLoginButton = findViewById(R.id.btn_login);
        mJoinButton = findViewById(R.id.btn_join);
        mResetPassword = findViewById(R.id.reset_password);

        cardview_login_underinfo = findViewById(R.id.cardview_login_underinfo);
        startAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_downtoup);
        endAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_uptodown);
        cardview_login_underinfo.startAnimation(startAnim);

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
                    // start lottie
                    upload_dialog(v);

                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        loginServer(txt_email, txt_password);
                                    } else {
                                        // end lottie
                                        alertDialog.dismiss();
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
        mRESTApi.login(mEmail,mPassword).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse");

                User Result = (User) response.body();
                user = Result;

                String code = response.headers().get("code");

//                Toast.makeText(getApplicationContext(),"code = "+response.headers() , Toast.LENGTH_SHORT).show();

                if (code != null && code.equals("00")) {
                    Log.d(TAG, "code.equlas('00')");

                    //Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENsGTH_SHORT).show();
                    // end lottie
                    alertDialog.dismiss();
                    cardview_login_underinfo.startAnimation(endAnim);

                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable()  {
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user",user);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                    }, 400); // 0.5초후
                } else {
                    Toast.makeText(getApplicationContext(),"로그인 실패 response code : " + code, Toast.LENGTH_SHORT).show();
                    // end lottie
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_SHORT).show();
                // end lottie
                // todo : 아이디가 서버에 없는 경우 통신 실패로 넘어오는 듯
                alertDialog.dismiss();
            }
        });
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        animationView = dialogView.findViewById(R.id.lottie_progress);
        animationView.playAnimation();
    }
}
