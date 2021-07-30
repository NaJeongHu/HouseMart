package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.publicdatacompetition.Model.House;
import com.example.publicdatacompetition.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_chat, ll_logout;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        init();
    }

    private void init() {
        ll_logout = findViewById(R.id.ll_logout);
        ll_chat = findViewById(R.id.ll_chatting);

        ll_logout.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        mUser = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_logout:
                upload_dialog(view);
                break;

            case R.id.ll_chatting:
                Intent intent = new Intent(SettingActivity.this, ChatListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_logout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
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
}
