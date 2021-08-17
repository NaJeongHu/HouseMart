package com.howsmart.housemart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.howsmart.housemart.Model.User;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_chat, ll_logout, ll_privateinfo, ll_business, ll_broker;
    private User mUser;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();

    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);

        ll_logout = findViewById(R.id.ll_logout);
        ll_chat = findViewById(R.id.ll_chatting);
        ll_privateinfo = findViewById(R.id.ll_privateinfo);
        ll_business = findViewById(R.id.ll_business);
        ll_broker = findViewById(R.id.ll_broker);

        ll_logout.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        ll_privateinfo.setOnClickListener(this);
        ll_business.setOnClickListener(this);
        ll_broker.setOnClickListener(this);
        mUser = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.ll_logout:
                upload_dialog(view);
                break;

            case R.id.ll_chatting:
                Intent intent = new Intent(SettingActivity.this, ChatListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.ll_privateinfo:
                Intent intent1 = new Intent(SettingActivity.this, PrivateinfoActivity.class);
                intent1.putExtra("user",mUser);
                startActivity(intent1);
                break;

            case R.id.ll_business:
                Intent intent2 = new Intent(SettingActivity.this, BusinessActivity.class);
                startActivity(intent2);
                break;

            case R.id.ll_broker:
                if(mUser.getQualification().equals("QUALIFIED")){
                    Intent intent3 = new Intent(SettingActivity.this, BrokerActivity.class);
                    intent3.putExtra("user",mUser);
                    startActivity(intent3);
                }
                else{
                    upload_dialog2(view);
                }
                break;
        }
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button cancle_btn = dialogView.findViewById(R.id.btn_cancel);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void upload_dialog2(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_broker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_broker);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent4 = new Intent(SettingActivity.this,BrokerCertificationActivity.class);
                intent4.putExtra("user",mUser);
                startActivity(intent4);
            }
        });

        Button cancle_btn = dialogView.findViewById(R.id.btn_no_broker);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
