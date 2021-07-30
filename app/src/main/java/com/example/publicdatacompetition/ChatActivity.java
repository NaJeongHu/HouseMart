package com.example.publicdatacompetition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    private ImageView image_view_back;
    private ImageView image_view_search;
    private ImageView image_view_plus;
    private ImageView image_view_send;
    private TextView text_view_chatter_name;
    private EditText edit_text_send;

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

        Log.d(TAG, "onCreate()...");

        image_view_back = findViewById(R.id.chat_btn_back);
        image_view_search = findViewById(R.id.chat_search);
        image_view_plus = findViewById(R.id.chat_option_plus);
        image_view_send = findViewById(R.id.chat_btn_send);
        text_view_chatter_name = findViewById(R.id.chat_chatter_name);
        edit_text_send = findViewById(R.id.chat_text_send);

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
                // TODO : 옵션 보여주기 기능
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
        }
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
    protected void onPause() {
        super.onPause();
        if(seenListener != null) {
            reference.removeEventListener(seenListener);
        }
    }
}