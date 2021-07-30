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

    private RelativeLayout relative_layout_bottom;
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



    private Chatter chatter;
    private Chatter myChatter;
    private String sumId;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private List<Chat> mchat;

    private ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        relative_layout_bottom = findViewById(R.id.chat_bottom);
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

        //getIntent and get Chatter
        Intent intent = getIntent();
        chatter = (Chatter) intent.getSerializableExtra("chatter");

        text_view_chatter_name.setText(chatter.getNickname());

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        //get combineId(real-time/Chats child name)
        sumId = getSumId(fuser.getUid(), chatter.getId());

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(fuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myChatter = snapshot.getValue(Chatter.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getDetails());
            }
        });

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

        recyclerView = findViewById(R.id.recyelr_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        readMessage();
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
                Log.d(TAG, "image_view_plus.getTag(): " + image_view_plus.getTag());
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
                write_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(write_intent);
                break;

            case R.id.chat_read_contract:
            case R.id.chat_txt_read_contract:
                Intent read_intent = new Intent(ChatActivity.this, ShowContractActivity.class);
                read_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(read_intent);
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

        reference.child("history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    mchat.add(chat);
                }

                chatAdapter = new ChatAdapter(ChatActivity.this, mchat, chatter.getImageURL());
                recyclerView.setAdapter(chatAdapter);

                seenMessage();
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

    private void seenMessage() {

        reference = FirebaseDatabase.getInstance().getReference("Chats").child(sumId);
        seenListener = reference.child("history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if(chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(chatter.getId())) {
                        // Chats에 메시지 본 여부 체크하기
                        HashMap<String, Object> isSeenHashMap = new HashMap<>();
                        isSeenHashMap.put("isseen", true);
                        dataSnapshot.getRef().updateChildren(isSeenHashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("lastMessage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Chat chat = snapshot.getValue(Chat.class);
                if(chat != null && chat.getReceiver().equals(fuser.getUid()) && chat.getSender().equals(chatter.getId())){
                    HashMap<String, Object> isSeenHashMap = new HashMap<>();
                    isSeenHashMap.put("isseen", true);
                    snapshot.getRef().updateChildren(isSeenHashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        chatAdapter.addChat(new Chat(sender, receiver, message, false, timestamp));
        recyclerView.setAdapter(chatAdapter);
//        chatAdapter.notifyDataSetChanged();
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
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(seenListener != null) {
            reference.removeEventListener(seenListener);
        }
    }
}