package com.example.publicdatacompetition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditId, mEditPassword, mEditPasswordConfirm, mEditPhoneNumber, mEditName, mEditNicname;
    private ImageView mIvPicture;
    private Button mJoinButton;
    private static final int REQUEST_CODE = 0;
    private Bitmap img;
    private String mId, mPassword, mPasswordConfirm, mPhoneNumber, mName, mNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEditId = findViewById(R.id.edit_join_id);
        mEditPassword = findViewById(R.id.edit_join_password);
        mEditPasswordConfirm = findViewById(R.id.edit_join_password_confirm);
        mEditPhoneNumber = findViewById(R.id.edit_join_phonenumber);
        mEditName = findViewById(R.id.edit_join_name);
        mEditNicname = findViewById(R.id.edit_join_nickname);
        mJoinButton = findViewById(R.id.btn_join_join);
        mIvPicture = findViewById(R.id.iv_join_picture);

        mIvPicture.setOnClickListener(this);
        mJoinButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_join_picture:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_join_join:
                // 에딧텍스트 다 채워져 있는지, 무결성 검사 진행해야함.
                // 비트맵 null인지 아닌지 검사해서 있으면 서버로 보내고, 없으면 null 서버로 보냄
                sendUserInfoToServer();
                break;
        }
    }

    private void sendUserInfoToServer() {
        File imageFile = null;

        if (img != null) {
            try {
                imageFile = savebitmap(img);
            } catch (Exception e) {}
        }

        //create a file to write bitmap data
        File f = new File(this.getCacheDir(), "filename");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        //Bitmap bitmap = your bitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mId = mEditId.getText().toString();
        mPassword = mEditPassword.getText().toString();
        mPasswordConfirm = mEditPasswordConfirm.getText().toString();
        mPhoneNumber = mEditPhoneNumber.getText().toString();
        mName = mEditName.getText().toString();
        mNickname = mEditNicname.getText().toString();

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",
                f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.joinRequest(mId,mPassword,mPasswordConfirm,mPhoneNumber,mName,mNickname,filePart)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("JoinActivity", "통신 성공 !");
                        Log.d("JoinActivity", "통신 성공 !" + response.message());
                        Log.d("JoinActivity", "통신 성공 !" + response.code());
                        Log.d("JoinActivity", "통신 성공 !" + response.headers());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Log.d("JoinActivity", "통신 실패 !");
                    }
                });
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "temp.png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    mIvPicture.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
