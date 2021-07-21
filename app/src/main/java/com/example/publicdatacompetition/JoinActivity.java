package com.example.publicdatacompetition;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditEmail, mEditPassword, mEditPasswordConfirm, mEditPhoneNumber, mEditName, mEditNicname;
    private ImageView mIvPicture;
    private Button mJoinButton;
    private static final int IMAGE_REQUEST = 0;
    private Bitmap img;
    private String mId, mPassword, mPasswordConfirm, mPhoneNumber, mName, mNickname;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser fuser;

    private StorageReference storageReference;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mEditEmail = findViewById(R.id.edit_join_email);
        mEditPassword = findViewById(R.id.edit_join_password);
        mEditPasswordConfirm = findViewById(R.id.edit_join_password_confirm);
        mEditPhoneNumber = findViewById(R.id.edit_join_phonenumber);
        mEditName = findViewById(R.id.edit_join_name);
        mEditNicname = findViewById(R.id.edit_join_nickname);
        mJoinButton = findViewById(R.id.btn_join_join);
        mIvPicture = findViewById(R.id.iv_join_picture);

        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

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
                startActivityForResult(intent, IMAGE_REQUEST);
                break;

            case R.id.btn_join_join:
                String txt_email = mEditEmail.getText().toString();
                String txt_password = mEditPassword.getText().toString();
                String txt_password_confirm = mEditPasswordConfirm.getText().toString();
                String txt_username = mEditName.getText().toString();
                String txt_nickname = mEditNicname.getText().toString();
                String txt_phone = mEditPhoneNumber.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_nickname) || TextUtils.isEmpty(txt_phone)){
                    Toast.makeText(this, "모든 정보를 채워주세요", Toast.LENGTH_SHORT).show();
                } else if(!checkPass(txt_password)){
                    Toast.makeText(this, "비밀번호는 영문, 숫자를 포함하여 8~20자로 작성해주세요.", Toast.LENGTH_SHORT).show();
                } else if(!txt_password.equals(txt_password_confirm)){
                    Toast.makeText(this, "비밀번호 재입력이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    register(txt_email, txt_password, txt_username, txt_nickname, txt_phone);
                }
                break;
        }
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

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = JoinActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(JoinActivity.this); //context에 표시하는 progressdialog 인스턴스 생성
        pd.setMessage("Uploading"); //progressdialog에 나타낼 text
        pd.show(); //progressdialog를 보여주기

        if(imageUri != null) { //intent의 결과로 imageUri가 넘어왔다면,
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() //FirebaseStorage 내부에 upload 내부에
                    +"."+getFileExtension(imageUri)); //"현재시간.형식"라는 이름의 StorageReference 만들기

            uploadTask = fileReference.putFile(imageUri); //FirebaseStorage에 file을 추가하는 Task를 StorageTask에 할당
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl(); //file의 다운로드 url 반환
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(JoinActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(JoinActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(JoinActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(JoinActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void register(String email, String password, final String username, final String nickname, final String phone) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            fuser = auth.getCurrentUser(); // 현재 사용자 가져오기
                            fuser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                uploadImage();

                                                String userid = fuser.getUid(); // 현재 사용자의 userid

                                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("id", userid);
                                                hashMap.put("username", username);
                                                hashMap.put("nickname", nickname);
                                                hashMap.put("imageURL", "default");
                                                hashMap.put("phone", phone);
                                                hashMap.put("search", username.toLowerCase());

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            sendUserInfoToServer();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(JoinActivity.this, "다른 이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

        img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
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

        mId = mEditEmail.getText().toString();
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
                        Log.d("JoinActivity", "통신 성공 !" + response.body().toString());
                        Log.d("JoinActivity", "통신 성공 !" + response.code());
                        Log.d("JoinActivity", "통신 성공 !" + response.headers());

                        String test = response.headers().get("code");

                        if (test.equals("00")) {
                            //로그인부터 해야할 것 같아서 LoginActivity로 넘어가도록 함
                            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(JoinActivity.this,"회원가입 실패" + test , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Log.d("JoinActivity", "통신 실패 !");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST){
            if(resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //change Uri to Bitmap
                try {
                    InputStream in = getContentResolver().openInputStream(imageUri);

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //set Image to mIvPicture
                if (imageUri != null) {
                    mIvPicture.setImageURI(imageUri);
                }
            } else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    //password check policy
    public boolean checkPass(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        // 숫자, 대문자, 소문자 포함
        // 8자 ~ 20자 허용
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
