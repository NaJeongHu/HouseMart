package com.howsmart.housemart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GPSTracker gps;
    private CardView cardview_main_profile, cardview_main_sell, cardview_main_buyA, cardview_main_buyV, cardview_main_buyO, cardview_main_broker, cardview_main_gochat;
    private double mLatitude, mLongitude;
    private String mSido, mSigungu;
    private User mUser;
    private ImageView iv_main_userprofile;
    private TextView tv_main_welcome;

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
        mUser = (User) getIntent().getSerializableExtra("user");
        getUserInfoFromServer();

        cardview_main_profile = findViewById(R.id.cardview_main_profile);
        cardview_main_sell = findViewById(R.id.cardview_main_sell);
        cardview_main_buyA = findViewById(R.id.cardview_main_buyA);
        cardview_main_buyV = findViewById(R.id.cardview_main_buyV);
        cardview_main_buyO = findViewById(R.id.cardview_main_buyO);
        cardview_main_broker = findViewById(R.id.cardview_main_broker);
        cardview_main_gochat = findViewById(R.id.cardview_main_gochat);

        tv_main_welcome = findViewById(R.id.tv_main_welcome);
        tv_main_welcome.setText("안녕하세요, " + mUser.getNickname() + "님!");
        iv_main_userprofile = findViewById(R.id.iv_main_userprofile);
        Glide.with(this).load(mUser.getImgUrl()).into(iv_main_userprofile);

        cardview_main_profile.setOnClickListener(this);
        cardview_main_sell.setOnClickListener(this);
        cardview_main_buyA.setOnClickListener(this);
        cardview_main_buyV.setOnClickListener(this);
        cardview_main_buyO.setOnClickListener(this);
        cardview_main_broker.setOnClickListener(this);
        cardview_main_gochat.setOnClickListener(this);

        // todo : mUser에서 중개사 자격 여부 확인해서 히든메뉴 온오프
        if (mUser.getQualification().equals("QUALIFIED")) {
            cardview_main_broker.setVisibility(View.VISIBLE);
        }
    }

    private void getUserInfoFromServer() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getUserInfo(mUser.getUserId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User Result = (User) response.body();
                mUser = Result;

                if (response.headers().get("code") != null && response.headers().get("code").equals("00")) {
                    Log.d("MainActivity","onResponse" + response.headers().get("code"));
                } else {
                    Log.d("MainActivity","onResponse" + response.headers().get("code"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("MainActivity","onFailure");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardview_main_profile:
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                i.putExtra("user",mUser);
                startActivity(i);
                break;
            case R.id.cardview_main_sell:
                upload_dialog(view);
                break;
            case R.id.cardview_main_buyA:
                Intent ii = new Intent(MainActivity.this, ListActivity.class);
                ii.putExtra("subject","아파트");
                startActivity(ii);
                break;
            case R.id.cardview_main_buyV:
                Intent iii = new Intent(MainActivity.this, ListActivity.class);
                iii.putExtra("subject","빌라");
                startActivity(iii);
                break;
            case R.id.cardview_main_buyO:
                Intent iiii = new Intent(MainActivity.this, ListActivity.class);
                iiii.putExtra("subject","오피스텔");
                startActivity(iiii);
                break;
            case R.id.cardview_main_broker:
                break;
            case R.id.cardview_main_gochat:
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

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_preupload, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        EditText mEditroomcnt = dialogView.findViewById(R.id.edit_preupload_roomcnt);
        EditText mEdittoiletcnt = dialogView.findViewById(R.id.edit_preupload_toiletcnt);

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_dialog_preupload);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

//                Intent intent = new Intent(getBaseContext(), UploadActivity.class);
//                intent.putExtra("roomcnt","1");
//                intent.putExtra("toiletcnt","1");
//                intent.putExtra("subject","아파트");
//                startActivity(intent);

                if (!mEditroomcnt.getText().toString().equals("") && !mEdittoiletcnt.getText().toString().equals("")) {
                    Intent intent = new Intent(getBaseContext(), UploadActivity.class);
                    intent.putExtra("user",mUser);
                    intent.putExtra("roomcnt",mEditroomcnt.getText().toString());
                    intent.putExtra("toiletcnt",mEdittoiletcnt.getText().toString());
                    intent.putExtra("subject","아파트");
                    startActivity(intent);
                } else {

                }



//                Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                startActivityForResult(intent,0);
            }
        });

        Button cancle_btn = dialogView.findViewById(R.id.btn_no_dialog_preupload);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}