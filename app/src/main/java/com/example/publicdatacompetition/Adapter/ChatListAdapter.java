package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.publicdatacompetition.ChatActivity;
import com.example.publicdatacompetition.Model.Chat;
import com.example.publicdatacompetition.Model.Chatter;
import com.example.publicdatacompetition.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context mContext;
    private List<Chatter> mChatters;

    private String theLastMessage;
    private String theLastDateOrTime;

    public ChatListAdapter(Context mContext, List<Chatter> mChatters){
        this.mContext = mContext;
        this.mChatters = mChatters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_list, parent, false);
        return new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chatter chatter = mChatters.get(position);

        holder.nickname.setText(chatter.getNickname());
        if(chatter.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.preview);
        } else {
            Glide.with(mContext).load(chatter.getImageURL()).into(holder.profile_image);
        }

        lastMessage(chatter.getId(), holder.last_msg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("nickname", chatter.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChatters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView profile_image;
        public TextView nickname;
        public TextView date;
        public TextView last_msg;
        public ImageView house_image;
        public TextView not_read;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.chat_list_profile_image);
            nickname = itemView.findViewById(R.id.chat_list_nickname);
            date = itemView.findViewById(R.id.chat_list_date);
            last_msg = itemView.findViewById(R.id.chat_list_last_msg);
            house_image = itemView.findViewById(R.id.chat_list_img_house);
            not_read = itemView.findViewById(R.id.chat_list_not_read);
        }
    }

    //check for last message
    private void lastMessage(String userid, TextView last_msg) {

        theLastMessage = "default";
        theLastDateOrTime = "default";

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chat.getMessage();
                    }
                }

                if(theLastMessage.equals("default")) {
                    last_msg.setText("");
                } else {
                    last_msg.setText(theLastMessage);
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

