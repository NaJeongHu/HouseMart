package com.howsmart.housemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.howsmart.housemart.Adapter.ChatListAdapter;
import com.howsmart.housemart.Model.Chat;
import com.howsmart.housemart.Model.Chatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ChatListActivity";

    private ImageView mBack;
    private ImageView mSearch;
    private EditText mEditTextSearch;
    private ImageView mSearchCancel;

    private InputMethodManager mInputMethodManager;

    private RecyclerView recyclerView;
    private ChatListAdapter mChatListAdapter;

    private List<Chatter> mChatters;
    private List<Chat> mLastMessages;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        mBack = findViewById(R.id.chat_list_btn_back);
        mSearch = findViewById(R.id.chat_list_search);
        mSearchCancel = findViewById(R.id.chat_list_search_cancel);

        mBack.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSearchCancel.setOnClickListener(this);

        mEditTextSearch = findViewById(R.id.chat_list_search_edit_text);
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    mSearchCancel.setVisibility(View.GONE);
                } else {
                    mSearchCancel.setVisibility(View.VISIBLE);
                }

                List<Chatter> searchChatters = new ArrayList<>();
                List<Chat> searchLastMessages = new ArrayList<>();

                for(int i = 0; i < mChatters.size(); ++i) {
                    if(mChatters.get(i).getNickname().contains(s)){
                        searchChatters.add(mChatters.get(i));
                        searchLastMessages.add(mLastMessages.get(i));
                    }
                }

                ChatListAdapter searchAdapter = new ChatListAdapter(ChatListActivity.this, searchChatters, searchLastMessages);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_list_btn_back:
                finish();
                break;

            case R.id.chat_list_search:
                if(mEditTextSearch.getVisibility() == View.VISIBLE) {
                    backToList();
                } else {
                    mSearch.setVisibility(View.GONE);
                    mEditTextSearch.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.chat_list_search_cancel:
                mEditTextSearch.setText("");
                break;
        }
    }

    private void getList() {

        mChatters = new ArrayList<>();
        mLastMessages = new ArrayList<>();

        //fuser과 채팅한 사람들의 목록 불러오기
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.orderByChild("users/" + fuser.getUid() + "/id").equalTo(fuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatters.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot usersSnapshot : dataSnapshot.child("users").getChildren()) {
                        Chatter chatter = usersSnapshot.getValue(Chatter.class);

                        //get chatter chatting with fuser
                        if (!chatter.getId().equals(fuser.getUid())) {
                            mChatters.add(chatter);

                            //get lastMessage
                            Chat lastmessage = dataSnapshot.child("lastMessage").getValue(Chat.class);
                            mLastMessages.add(lastmessage);
                        }
                    }
                }

                mChatListAdapter = new ChatListAdapter(ChatListActivity.this, mChatters, mLastMessages);
                recyclerView.setAdapter(mChatListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void backToList() {
        recyclerView.setAdapter(mChatListAdapter);
        mEditTextSearch.setVisibility(View.GONE);
        mEditTextSearch.setText("");
        mInputMethodManager.hideSoftInputFromWindow(mEditTextSearch.getWindowToken(), 0);
        mSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(mEditTextSearch.getVisibility() == View.VISIBLE) {
            backToList();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        getList();
    }
}