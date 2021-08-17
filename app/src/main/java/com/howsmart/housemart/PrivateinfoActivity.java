package com.howsmart.housemart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivateinfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name, nickname, phonenumber, birth;
    private ImageView picture, btn_back_privateinfo;
    private LinearLayout linear_nickname, linear_birth, linear_password;

    private Uri imageUri;
    private User mUser;
    private MultipartBody.Part new_picture;
    private static final int IMAGE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privateinfo);
        init();
    }

    private void init() {
        mUser = (User) getIntent().getSerializableExtra("user");

        btn_back_privateinfo = findViewById(R.id.btn_back_privateinfo);
        picture = findViewById(R.id.iv_privateinfo_picture);
        name = findViewById(R.id.tv_privateinfo_name);
        nickname = findViewById(R.id.tv_privateinfo_nickname);
        phonenumber = findViewById(R.id.tv_privateinfo_phonenumber);
        birth = findViewById(R.id.tv_privateinfo_birth);
        linear_birth = findViewById(R.id.linear_birth);
        linear_nickname = findViewById(R.id.linear_nickname);
        linear_password = findViewById(R.id.linear_password);

        btn_back_privateinfo.setOnClickListener(this);
        picture.setOnClickListener(this);
        linear_birth.setOnClickListener(this);
        linear_nickname.setOnClickListener(this);
        linear_password.setOnClickListener(this);

        setPrivateInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_privateinfo_picture:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
                break;
            case R.id.linear_birth:
                Intent intent1 = new Intent(PrivateinfoActivity.this, ChangeBirthActivity.class);
                intent1.putExtra("userId", mUser.getUserId());
                startActivity(intent1);
                break;
            case R.id.linear_nickname:
                Intent intent2 = new Intent(PrivateinfoActivity.this, ChangeNicknameActivity.class);
                intent2.putExtra("userId", mUser.getUserId());
                startActivity(intent2);
                break;
            case R.id.linear_password:
                Intent intent3 = new Intent(PrivateinfoActivity.this, ChangePasswordActivity.class);
                intent3.putExtra("userId", mUser.getUserId());
                startActivity(intent3);
                break;
            case R.id.btn_back_privateinfo:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfoFromServer();
    }

    private void getUserInfoFromServer() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getUserInfo(mUser.getUserId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User Result = (User) response.body();
                mUser = Result;

                if (response.headers().get("code") != null && response.headers().get("code").equals("00")) {
                    Log.d("MainActivity", "onResponse" + response.headers().get("code"));
                } else {
                    Log.d("MainActivity", "onResponse" + response.headers().get("code"));
                }
                setPrivateInfo();
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("MainActivity", "onFailure");
            }
        });
    }

    private void setPrivateInfo() {
        Glide.with(PrivateinfoActivity.this).load(mUser.getImgUrl()).into(picture);
        name.setText(mUser.getName());
        nickname.setText(mUser.getNickname());
        phonenumber.setText(mUser.getPhoneNumber());
        birth.setText(mUser.getIdNum());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    picture.setImageURI(imageUri);
                    transUriToMultiPartFile(imageUri);
                    doRetrofit();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 변경 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void transUriToMultiPartFile(Uri uri) {
        Bitmap img = null;

        //change Uri to Bitmap
        if (uri != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    img = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                } else {
                    img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            img = BitmapFactory.decodeResource(getResources(), R.drawable.icon_parking);
        }

        try {
            String filenameJPEG = "file" + ".jpg";
            File f = new File(this.getCacheDir(), filenameJPEG);
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, fos);
            fos.close();

            String filename = "file";

            new_picture = MultipartBody.Part.createFormData(filename, f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRetrofit() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.changePicture(mUser.getUserId(), new_picture).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String test_code = response.headers().get("code");

                if (test_code != null && test_code.equals("00")) {
                    getUserInfoFromServer();
                } else {
                    Toast.makeText(PrivateinfoActivity.this, "사진 변경 실패" + test_code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("PrivateinfoActivity", "failure change picture", throwable);
            }

        });
    }
}