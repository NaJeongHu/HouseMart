package com.example.publicdatacompetition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicdatacompetition.Adapter.ChatAdapter;
import com.example.publicdatacompetition.Model.Chat;
import com.example.publicdatacompetition.Model.Chatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChatActivity";

    private static final int IMAGE_REQUEST = 0;
    private static final int CAMERA_REQUEST = 1;
    private static final int WRITE_REQUEST = 2;
    private static final int READ_REQUEST = 3;

    private ConstraintLayout constraint_layout_option;

    private ImageView image_view_back;
    private ImageView image_view_search;
    private ImageView image_view_plus;
    private ImageView image_view_send;
    private TextView text_view_chatter_name;
    private EditText edit_text_send;

    private ImageView image_view_write;
    private ImageView image_view_read;
    private ImageView image_view_contract;
    private ImageView image_view_call;
    private ImageView image_view_album;
    private ImageView image_view_camera;
    private ImageView image_view_phase;
    private TextView text_view_write;
    private TextView text_view_read;
    private TextView text_view_contract;
    private TextView text_view_call;
    private TextView text_view_album;
    private TextView text_view_camera;
    private TextView text_view_phase;

    private Long house_idx;
    private Long contract_idx;

    private Chatter chatter;
    private Chatter myChatter;
    private String sumId;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private List<Chat> mchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        constraint_layout_option = findViewById(R.id.chat_option);

        image_view_back = findViewById(R.id.chat_btn_back);
        image_view_search = findViewById(R.id.chat_search);
        image_view_plus = findViewById(R.id.chat_option_plus);
        image_view_send = findViewById(R.id.chat_btn_send);
        text_view_chatter_name = findViewById(R.id.chat_chatter_name);
        edit_text_send = findViewById(R.id.chat_text_send);

        image_view_write = findViewById(R.id.chat_write_contract);
        image_view_read = findViewById(R.id.chat_read_contract);
        image_view_contract = findViewById(R.id.chat_contract);
        image_view_call = findViewById(R.id.chat_call);
        image_view_album = findViewById(R.id.chat_album);
        image_view_camera = findViewById(R.id.chat_camera);
        image_view_phase = findViewById(R.id.chat_phase);
        text_view_write = findViewById(R.id.chat_txt_write_contract);
        text_view_read = findViewById(R.id.chat_txt_read_contract);
        text_view_contract = findViewById(R.id.chat_txt_contract);
        text_view_call = findViewById(R.id.chat_txt_call);
        text_view_album = findViewById(R.id.chat_txt_album);
        text_view_camera = findViewById(R.id.chat_txt_camera);
        text_view_phase = findViewById(R.id.chat_txt_phase);

        image_view_back.setOnClickListener(this);
        image_view_search.setOnClickListener(this);
        image_view_plus.setOnClickListener(this);
        image_view_send.setOnClickListener(this);

        image_view_write.setOnClickListener(this);
        image_view_read.setOnClickListener(this);
        image_view_contract.setOnClickListener(this);
        image_view_call.setOnClickListener(this);
        image_view_album.setOnClickListener(this);
        image_view_camera.setOnClickListener(this);
        image_view_phase.setOnClickListener(this);
        text_view_write.setOnClickListener(this);
        text_view_read.setOnClickListener(this);
        text_view_contract.setOnClickListener(this);
        text_view_call.setOnClickListener(this);
        text_view_album.setOnClickListener(this);
        text_view_camera.setOnClickListener(this);
        text_view_phase.setOnClickListener(this);

        //getIntent and get Chatter
        Intent intent = getIntent();
        String chatter_id = intent.getStringExtra("FirebaseId");
        house_idx = intent.getLongExtra("houseIdx", -1);
        contract_idx = intent.getLongExtra("contractIdx", -1);

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        sumId = getSumId(fuser.getUid(), chatter_id); //get combineId(real-time/Chats child name)

        //get myChatter instance
        reference = FirebaseDatabase.getInstance().getReference("Users");
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
                        linearLayoutManager.setStackFromEnd(true);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn_back:
                onBackPressed();
                break;

            case R.id.chat_search:
                // TODO : 검색 기능
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
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy월 MM월 dd일 a hh:mm:ss", Locale.KOREA);
                    String date = transFormat.format(new Date());

                    sendMessage(fuser.getUid(), chatter.getId(), message, date);
                } else {
                    Toast.makeText(ChatActivity.this, "메세지를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                edit_text_send.setText("");
                break;

            case R.id.chat_write_contract:
            case R.id.chat_txt_write_contract:
                Intent write_intent = new Intent(ChatActivity.this, MakeContractActivity.class);
                write_intent.putExtra("buyer_phone", chatter.getPhone());
                write_intent.putExtra("seller_phone", myChatter.getPhone());
                write_intent.putExtra("house_idx", house_idx);
                write_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(write_intent, WRITE_REQUEST);
                break;

            case R.id.chat_read_contract:
            case R.id.chat_txt_read_contract:
                Intent read_intent = new Intent(ChatActivity.this, ShowContractActivity.class);
                read_intent.putExtra("contract_idx", contract_idx);
                read_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(read_intent, READ_REQUEST);
                break;

            case R.id.chat_contract:
            case R.id.chat_txt_contract:
                //TODO : make correct intent
//                Intent contract_intent = new Intent(ChatActivity.this, ??.class);
//                contract_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(contract_intent);
//                finish();
                break;

            case R.id.chat_call:
            case R.id.chat_txt_call:
                Intent call_intent = new Intent();
                call_intent.setAction(Intent.ACTION_DIAL);
                call_intent.setData(Uri.parse("tel:" + chatter.getPhone()));
                startActivity(call_intent);
                break;

            case R.id.chat_album:
            case R.id.chat_txt_album:
                Intent album_intent = new Intent();
                album_intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(album_intent, IMAGE_REQUEST);
                break;

            case R.id.chat_camera:
            case R.id.chat_txt_camera:
                Intent camera_intent = new Intent();
                camera_intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, CAMERA_REQUEST);
                break;

            case R.id.chat_phase:
            case R.id.chat_txt_phase:
                show_phase_diagram();
                break;
        }
    }

    private void show_phase_diagram() {
        //TODO : show phase diagram
    }

    private void readMessage(){

        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats").child(sumId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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

                //get houseIdx
                if(house_idx == -1) {
                    house_idx = snapshot.child("houseInfo").child("houseIdx").getValue(Long.class);
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("houseIdx", house_idx);
                    snapshot.child("houseInfo").getRef().setValue(hashMap);
                }

                //get contractIdx
                if(contract_idx == -1) {
                    contract_idx = snapshot.child("houseInfo").child("contractIdx").getValue(Long.class);
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("contractIdx", contract_idx);
                    snapshot.child("houseInfo").getRef().setValue(hashMap);
                }

                chatAdapter = new ChatAdapter(ChatActivity.this, mchat, chatter.getImageURL());
                recyclerView.setAdapter(chatAdapter);
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
//        chatAdapter.notifyDataSetChanged();
        }
    }

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

            //TODO : write code to add image to firebase Chats history
        } else if(requestCode == CAMERA_REQUEST && data != null){
            //TODO : write code to capture image and add to firebase Chats history
        } else if(requestCode == WRITE_REQUEST && data != null){
            contract_idx = data.getLongExtra("contractIdx", -1);
        } else if(requestCode == READ_REQUEST && data != null){

        }
    }
}