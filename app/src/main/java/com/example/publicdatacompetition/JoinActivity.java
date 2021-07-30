package com.example.publicdatacompetition;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.example.publicdatacompetition.Model.User;
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

    private static final String TAG = "JoinActivity";

    private EditText mEditEmail, mEditPassword, mEditPasswordConfirm, mEditPhoneNumber, mEditName, mEditNicname, mEditIdNum;
    private ImageView mIvPicture;
    private Button mJoinButton;
    private static final int IMAGE_REQUEST = 0;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser fuser;
    private User user;

    private StorageReference storageReference;
    private Uri imageUri;
    private StorageTask uploadTask;

    private ProgressDialog pd;

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
        mEditIdNum = findViewById(R.id.edit_join_idNum);
        mJoinButton = findViewById(R.id.btn_join_join);
        mIvPicture = findViewById(R.id.iv_join_picture);

        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profiles");

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
                String txt_idnum = mEditIdNum.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_nickname) || TextUtils.isEmpty(txt_phone)){
                    Toast.makeText(this, "모든 정보를 채워주세요", Toast.LENGTH_SHORT).show();
                } else if(!checkPass(txt_password)){
                    Toast.makeText(this, "비밀번호는 영문, 숫자를 포함하여 8~20자로 작성해주세요.", Toast.LENGTH_SHORT).show();
                } else if(!txt_password.equals(txt_password_confirm)){
                    Toast.makeText(this, "비밀번호 재입력이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    register(txt_email, txt_password, txt_password_confirm, txt_username, txt_nickname, txt_phone, txt_idnum);
                }
                break;
        }
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream;
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
        pd = new ProgressDialog(JoinActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null) { //intent의 결과로 imageUri가 넘어왔다면,
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
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
            Toast.makeText(JoinActivity.this, "선택된 이미지가 없습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String email, String password, String password_confirm, final String username, final String nickname, final String phone, final String idnum) {
        Log.d(TAG, "register(...)...");

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmailAndPassword...");

                            fuser = auth.getCurrentUser(); // 현재 사용자 가져오기
                            fuser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Log.d(TAG, "sendEmailVerification...");

                                                String userid = fuser.getUid(); // 현재 사용자의 userid

                                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("id", userid);
                                                hashMap.put("username", username);
                                                hashMap.put("nickname", nickname);
                                                hashMap.put("imageURL", "default");
                                                hashMap.put("phone", phone);
                                                hashMap.put("search", username.toLowerCase());
//                                                hashMap.put("idnum", idnum);

                                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Log.d(TAG, "setValue(hashMap)...");

                                                            if(imageUri != null) {
                                                                uploadImage();

                                                                Log.d(TAG, "uploadImage...");
                                                            }

                                                            sendUserInfoToServer(email, password, password_confirm, phone, username, nickname, idnum, userid);
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

    private void sendUserInfoToServer(String userId, String password, String password_confirm,  final String phone, final String username, final String nickname, final String idnum, String firebaseId) {
        Log.d(TAG, "sendUserInfoToServer...");

        File imageFile = null;
        MultipartBody.Part filePart = null;
        Bitmap img = null;

        //change Uri to Bitmap
        if(imageUri != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    img = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageUri));
                } else {
                    img = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            img  = BitmapFactory.decodeResource(getResources(), R.drawable.preview);
        }

        try {
            imageFile = savebitmap(img);

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //create a file to write bitmap data
            File f = new File(this.getCacheDir(), "filename");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

            filePart = MultipartBody.Part.createFormData("file",
                    f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "make imageUri...");

//        user = new User(userId,username,nickname,idnum);
//        user.setId(userId);
//        user.setIdnum(idnum);
//        user.setName(username);
//        user.setNickname(nickname);

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.joinRequest(userId,password,password_confirm,phone,username,nickname,idnum,firebaseId,filePart)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "joinRequest");

                        String test = response.headers().get("code");

                        if (test.equals("00")) {
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(JoinActivity.this,"회원가입 실패" + test , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Log.d("JoinActivity", throwable.getMessage());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST){
            if(resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(pd != null) {
            pd.dismiss();
        }
    }
}
