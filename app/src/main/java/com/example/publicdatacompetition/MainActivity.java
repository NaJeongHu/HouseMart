package com.example.publicdatacompetition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GPSTracker gps;

    public double mLatitude;
    public double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getpermisson();
        getLocation();

        // todo : data binding 없이 작동하는지 확인
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

    private void getLocation() {
        gps = new GPSTracker(MainActivity.this, this);
        mLatitude = 0;
        mLongitude = 0;

        if (gps.canGetLocation()) {
            mLatitude = gps.getLatitude();
            mLongitude = gps.getLongitude();

            if(mLatitude == 0 || mLongitude == 0){
                Toast.makeText(getApplicationContext(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
                mLatitude = 35.887515;
                mLongitude = 128.611553;
            }
        } else {
            gps.showSettingsAlert();
        }
    }

    private void getpermisson() {
        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permiCheck_loca == PackageManager.PERMISSION_DENIED) {
            Log.d("위치 권한 없는 상태", "");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Log.d("위치 권한 있는 상태", "");
        }
    }
}