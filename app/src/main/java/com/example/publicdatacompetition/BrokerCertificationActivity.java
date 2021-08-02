package com.example.publicdatacompetition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicdatacompetition.Model.User;

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

public class BrokerCertificationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_broker, btn_back;
    private TextView tv_broker;
    private AppCompatButton btn_upload;
    private static final int IMAGE_REQUEST = 0;

    private Uri imageUri;
    private User mUser;
    private MultipartBody.Part picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broker_certification);

        mUser = (User) getIntent().getSerializableExtra("user");

        btn_back = findViewById(R.id.btn_back);
        tv_broker = findViewById(R.id.tv_broker);
        image_broker = findViewById(R.id.image_broker);
        btn_upload = findViewById(R.id.btn_upload);

        image_broker.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_broker:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
                break;

            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.btn_upload:
                if (imageUri != null && (mUser.getQualification().equals("REJECTED") || mUser.getQualification().equals("NONE"))) {
                    transUriToMultiPartFile(imageUri);
                    doRetrofit();
                } else if (imageUri == null) {
                    Toast.makeText(BrokerCertificationActivity.this, "공인중개사 자격증 사진을 등록해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BrokerCertificationActivity.this, "관리자 승인을 기다리는 중입니다", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    image_broker.setImageURI(imageUri);
                    tv_broker.setVisibility(View.GONE);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
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
            String filenameJPEG = "certification" + ".jpg";
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

            String filename = "certification";

            picture = MultipartBody.Part.createFormData(filename, f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRetrofit() {
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.uploadCertificatioin(mUser.getUserId(), picture).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String test_code = response.headers().get("code");
                Log.d("testtest", "");
                Log.d("UploadActivity", "headercode" + test_code);
                if (test_code == null) {
                    Toast.makeText(BrokerCertificationActivity.this, "다시 시도해주세요" + test_code, Toast.LENGTH_SHORT).show();
                } else {
                    if(test_code.equals("00")){
                        Toast.makeText(BrokerCertificationActivity.this, "사진 등록 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BrokerCertificationActivity.this, SettingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(test_code.equals("05")){
                        Toast.makeText(BrokerCertificationActivity.this, "등록 대기중입니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BrokerCertificationActivity.this, SettingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(test_code.equals("01")){
                        Toast.makeText(BrokerCertificationActivity.this, "사진이 전송되지 않았습니다", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("UploadActivity", throwable.getMessage());
            }

        });
    }
}