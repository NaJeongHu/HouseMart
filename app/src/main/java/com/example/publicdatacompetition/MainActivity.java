package com.example.publicdatacompetition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo data binding 없이 작동하는지 확인
        btn_info = findViewById(R.id.btn_main_info);
        btn_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_info:
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
                break;
            case R.id.btn_main_buy_apart:
                Intent ii = new Intent(MainActivity.this, ListActivity.class);
                startActivity(ii);
                break;
            case R.id.btn_main_sell:
                Intent iii = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(iii);
                break;
        }
    }
}