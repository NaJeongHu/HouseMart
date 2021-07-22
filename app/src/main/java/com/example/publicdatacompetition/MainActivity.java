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
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GPSTracker gps;
    private Button mUserInfo, mBuy, mSell;
    private double mLatitude, mLongitude;
    private String mSido, mSigungu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getpermisson();
        getLocation();

        // todo : data binding 없이 작동하는지 확인
    }

    private void init() {
        mUserInfo = findViewById(R.id.btn_main_info);
        mBuy = findViewById(R.id.btn_main_buy_apart);
        mSell = findViewById(R.id.btn_main_sell);

        mUserInfo.setOnClickListener(this);
        mBuy.setOnClickListener(this);
        mSell.setOnClickListener(this);
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
                ii.putExtra("subject","아파트");
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

        final Geocoder geocoder = new Geocoder(this);

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

        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(mLatitude, mLongitude, 1); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            if (list.size()==0) {
                mSido = "대구광역시";
                mSigungu = "북구";
            } else {
                mSido = list.get(0).getAdminArea();
                if (list.get(0).getLocality() != null && list.get(0).getLocality().length() > 0) {
                    mSigungu = list.get(0).getLocality();
                } else {
                    mSigungu = list.get(0).getSubLocality();
                }
            }
        } else {
            mSido = "대구광역시";
            mSigungu = "북구";
        }

        MyLocation.getInstance().setLATITUDE(mLatitude);
        MyLocation.getInstance().setLONGITUDE(mLongitude);
        MyLocation.getInstance().setSIDO(mSido);
        MyLocation.getInstance().setSIGUNGU(mSigungu);
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