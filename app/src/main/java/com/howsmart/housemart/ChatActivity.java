package com.howsmart.housemart;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.howsmart.housemart.Adapter.ChatAdapter;
import com.howsmart.housemart.Model.Chat;
import com.howsmart.housemart.Model.Chatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.howsmart.housemart.Notifications.FirebaseApi;
import com.howsmart.housemart.Notifications.MyResponse;
import com.howsmart.housemart.Notifications.NotificationData;
import com.howsmart.housemart.Notifications.Sender;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChatActivityTag";

    private static final int IMAGE_REQUEST = 0;
    private static final int CAMERA_REQUEST = 1;
    private static final int WRITE_REQUEST = 2;
    private static final int READ_REQUEST = 3;

    private ConstraintLayout constraint_layout_option;

    private ImageView image_view_back;
    private EditText edit_text_search;
    private ImageView image_view_search;
    private ImageView image_view_cancel;
    private ImageView image_view_plus;
    private ImageView image_view_send;
    private TextView text_view_chatter_name;
    private EditText edit_text_send;
    private ImageView image_view_write;
    private ImageView image_view_read;
    private ImageView image_view_call;
    private ImageView image_view_album;
    private ImageView image_view_camera;
    private ImageView image_view_phase;

    private InputMethodManager inputMethodManager;

    private Long houseIdx;
    private Long contractIdx;
    private String buyerPhone;
    private String sellerPhone;

    private Chatter chatter;
    private Chatter myChatter;
    private String sumId;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    private FirebaseUser fuser;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private ValueEventListener readValueEventListener;

    private List<Chat> mchat;
    private List<Chat> msearchChat;

    private FirebaseApi firebaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        constraint_layout_option = findViewById(R.id.chat_option);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        image_view_back = findViewById(R.id.chat_btn_back);
        edit_text_search = findViewById(R.id.chat_search_edit_text);
        image_view_search = findViewById(R.id.chat_search);
        image_view_cancel = findViewById(R.id.chat_search_cancel);
        image_view_plus = findViewById(R.id.chat_option_plus);
        image_view_send = findViewById(R.id.chat_btn_send);
        text_view_chatter_name = findViewById(R.id.chat_chatter_name);
        edit_text_send = findViewById(R.id.chat_text_send);

        image_view_write = findViewById(R.id.chat_write_contract);
        image_view_read = findViewById(R.id.chat_read_contract);
        image_view_call = findViewById(R.id.chat_call);
        image_view_album = findViewById(R.id.chat_album);
        image_view_camera = findViewById(R.id.chat_camera);
        image_view_phase = findViewById(R.id.chat_phase);

        image_view_back.setOnClickListener(this);
        image_view_search.setOnClickListener(this);
        image_view_cancel.setOnClickListener(this);
        image_view_plus.setOnClickListener(this);
        image_view_send.setOnClickListener(this);

        image_view_write.setOnClickListener(this);
        image_view_read.setOnClickListener(this);
        image_view_call.setOnClickListener(this);
        image_view_album.setOnClickListener(this);
        image_view_camera.setOnClickListener(this);
        image_view_phase.setOnClickListener(this);

        edit_text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    image_view_cancel.setVisibility(View.GONE);
                } else {
                    image_view_cancel.setVisibility(View.VISIBLE);
                }

                msearchChat = new ArrayList<>();
                for (Chat chat : mchat) {
                    if(chat.getMessage().contains(s)){
                        msearchChat.add(chat);
                    }
                }

                ChatAdapter searchChatAdapter = new ChatAdapter(ChatActivity.this, msearchChat, chatter.getImageURL());
                recyclerView.setAdapter(searchChatAdapter);
                recyclerView.scrollToPosition(searchChatAdapter.getItemCount()-1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("chats");

        //getIntent and get Chatter
        Intent intent = getIntent();
        String chatter_id = intent.getStringExtra("FirebaseId");
        houseIdx = intent.getLongExtra("houseIdx", -1);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        sumId = getSumId(fuser.getUid(), chatter_id); //get combineId(real-time/Chats child name)

        //get myChatter instance
        reference = FirebaseDatabase.getInstance().getReference("Users");
        updateToken();
        reference.child(fuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myChatter = snapshot.getValue(Chatter.class);

                //get chatter instance
                reference.child(chatter_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatter = snapshot.getValue(Chatter.class);

                        text_view_chatter_name.setText(chatter.getNickname());

                        recyclerView = findViewById(R.id.recyelr_view);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);

                        readMessage();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "while getting chatter: " + error.getDetails());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "while getting myChatter: " + error.getDetails());
            }
        });
    }

    private void updateToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("token", token);
                        reference.child(fuser.getUid()).updateChildren(hashMap);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn_back:
                finish();
                break;

            case R.id.chat_search:
                if(edit_text_search.getVisibility() == View.VISIBLE){
                    backToChat();
                } else {
                    image_view_search.setVisibility(View.GONE);
                    edit_text_search.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.chat_search_cancel:
                edit_text_search.setText("");
                break;

            case R.id.chat_option_plus:
                if(image_view_plus.getTag().equals("plus")){
                    image_view_plus.setImageResource(R.drawable.cancel);
                    constraint_layout_option.setVisibility(View.VISIBLE);
                    image_view_plus.setTag("cancel");
                } else if(image_view_plus.getTag().equals("cancel")){
                    image_view_plus.setImageResource(R.drawable.chat_plus);
                    constraint_layout_option.setVisibility(View.GONE);
                    image_view_plus.setTag("plus");
                }
                break;

            case R.id.chat_btn_send: // add message to firebase database
                String message = edit_text_send.getText().toString();
                if(!message.equals("")) {
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss", Locale.KOREA);
                    String date = transFormat.format(new Date());

                    sendMessage(fuser.getUid(), chatter.getId(), message, date);
                } else {
                    Toast.makeText(ChatActivity.this, "메세지를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                edit_text_send.setText("");
                break;

            case R.id.chat_write_contract:
                if(sellerPhone.equals(myChatter.getPhone())) {
                    Intent write_intent = new Intent(ChatActivity.this, MakeContractActivity.class);
                    write_intent.putExtra("buyerPhone", buyerPhone);
                    write_intent.putExtra("sellerPhone", sellerPhone);
                    write_intent.putExtra("houseIdx", houseIdx);
                    write_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(write_intent, WRITE_REQUEST);
                } else{
                    Toast.makeText(this, "매도자만 가계약서를 작성할 수 있습니다", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.chat_read_contract:
                if(contractIdx != null && contractIdx != -1L) {
                    Intent read_intent = new Intent(ChatActivity.this, ShowContractActivity.class);
                    read_intent.putExtra("contractIdx", contractIdx);
                    read_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(read_intent, READ_REQUEST);
                } else {
                    Toast.makeText(this, "아직 작성된 가계약서가 없습니다", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.chat_call:
                Intent call_intent = new Intent();
                call_intent.setAction(Intent.ACTION_DIAL);
                call_intent.setData(Uri.parse("tel:" + chatter.getPhone()));
                startActivity(call_intent);
                break;

            case R.id.chat_album:
                Intent album_intent = new Intent();
                album_intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(album_intent, IMAGE_REQUEST);
                break;

            case R.id.chat_camera:
                Intent camera_intent = new Intent();
                camera_intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAMERA_REQUEST);
                break;

            case R.id.chat_phase:
                show_phase_diagram();
                break;
        }
    }

    private void backToChat() {
        recyclerView.setAdapter(chatAdapter);
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
        edit_text_search.setVisibility(View.GONE);
        edit_text_search.setText("");
        inputMethodManager.hideSoftInputFromWindow(edit_text_search.getWindowToken(), 0);
        image_view_search.setVisibility(View.VISIBLE);
    }

    private void show_phase_diagram() {
        //TODO : show phase diagram
    }

    private void readMessage(){

        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats").child(sumId);
        reference.child("houseInfo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get houseIdx or register houseIdx
                try {
                    houseIdx = snapshot.child("houseIdx").getValue(Long.class);
                } catch (Exception e) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("houseIdx", houseIdx);
                    hashMap.put("buyerPhone", myChatter.getPhone());
                    hashMap.put("sellerPhone", chatter.getPhone());
                    snapshot.child("houseInfo").getRef().updateChildren(hashMap);
                }

                //get buyerPhone, sellerPhone, and contractIdx
                buyerPhone = snapshot.child("buyerPhone").getValue(String.class);
                sellerPhone = snapshot.child("sellerPhone").getValue(String.class);
                contractIdx = snapshot.child("contractIdx").getValue(Long.class);
                if(contractIdx == null){
                    contractIdx = -1L;
                }

                //read message
                readValueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mchat.clear();
                        for (DataSnapshot dataSnapshot : snapshot.child("history").getChildren()) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            mchat.add(chat);

                            // Chats에 메시지 본 여부 체크하기
                            if(chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(chatter.getId())) {
                                HashMap<String, Object> isSeenHashMap = new HashMap<>();
                                isSeenHashMap.put("isseen", true);
                                dataSnapshot.getRef().updateChildren(isSeenHashMap);
                            }
                        }

                        chatAdapter = new ChatAdapter(ChatActivity.this, mchat, chatter.getImageURL());
                        recyclerView.setAdapter(chatAdapter);
                        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                reference.addValueEventListener(readValueEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getSumId(String uid, String chatterid) {
        if(uid.compareTo(chatterid) < 0) {
            return uid + chatterid;
        } else {
            return chatterid + uid;
        }
    }

    private void sendMessage(String sender, String receiver, String message, String timestamp){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats").child(sumId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("timestamp", timestamp);

        reference.child("history").push().setValue(hashMap);
        reference.child("lastMessage").setValue(hashMap);
        reference.child("users").child(fuser.getUid()).setValue(myChatter);
        reference.child("users").child(chatter.getId()).setValue(chatter);

        if(mchat != null){
            Chat newChat = new Chat(sender, receiver, message, false, timestamp);
            mchat.add(newChat);
            chatAdapter = new ChatAdapter(ChatActivity.this, mchat, chatter.getImageURL());
            recyclerView.setAdapter(chatAdapter);
            recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
        }

        sendNotification(message);
    }

    private void sendNotification(String message) {
        String to = chatter.getToken();
        NotificationData data = new NotificationData(chatter.getNickname(), message, chatter.getId(), myChatter.getId());
        Sender sender = new Sender(to, data);

        firebaseApi = FirebaseApi.retrofit.create(FirebaseApi.class);
        firebaseApi.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(response.code() == 200){
                    if(response.body().success != 1){
                        Log.d(TAG, "noti 보내기 실패");
                    } else {
                        Log.d(TAG, "noti 보내기 성공");
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable throwable) {
                Log.d(TAG, throwable.getMessage());
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = ChatActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

//    private void sendImage(Uri imageUri) {
//        if(imageUri != null) {
//            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() +"."+getFileExtension(imageUri));
//
//            StorageTask uploadTask = fileReference.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if(!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//
//                    return fileReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//                        String mUri = downloadUri.toString();
//
//                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("imageURL", mUri);
//                        reference.updateChildren(map);
//
//                        pd.dismiss();
//                    } else {
//                        Toast.makeText(JoinActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(JoinActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(JoinActivity.this, "선택된 이미지가 없습니다", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat_mute:
                return true;

//            case R.id.menu_chat_block:
//                return true;

            case R.id.menu_chat_leave:
                return true;

            default:
                return false;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;

        if(requestCode == IMAGE_REQUEST && data != null) {
            Uri imageUri = data.getData();
//            sendImage(imageUri);
        } else if(requestCode == CAMERA_REQUEST && data != null){
            //TODO : write code to capture image and add to firebase Chats history
        } else if(requestCode == WRITE_REQUEST && data != null){
            contractIdx = data.getLongExtra("contractIdx", -1L);

            if(contractIdx != -1L) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats").child(sumId);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("contractIdx", contractIdx);
                reference.child("houseInfo").updateChildren(hashMap);
            }

        } else if(requestCode == READ_REQUEST && data != null){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(readValueEventListener);
    }

    @Override
    public void onBackPressed() {
        if(edit_text_search.getVisibility() == View.VISIBLE){
            backToChat();
        } else {
            super.onBackPressed();
        }
    }
}