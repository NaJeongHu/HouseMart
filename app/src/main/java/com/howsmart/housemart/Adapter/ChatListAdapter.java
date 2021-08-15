package com.howsmart.housemart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.ChatActivity;
import com.howsmart.housemart.Model.Chat;
import com.howsmart.housemart.Model.Chatter;
import com.howsmart.housemart.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context mContext;
    private List<Chatter> mChatters;
    private List<Chat> mLastMessages;

    public ChatListAdapter(Context mContext, List<Chatter> mChatters, List<Chat> mLastMessages){
        this.mContext = mContext;
        this.mChatters = mChatters;
        this.mLastMessages = mLastMessages;
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
        Chat lastMessage = mLastMessages.get(position);

        holder.nickname.setText(chatter.getNickname());
        if(chatter.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.preview);
        } else {
            Glide.with(mContext).load(chatter.getImageURL()).into(holder.profile_image);
        }
        holder.last_date.setText(lastMessage.getTimestamp().substring(6, 14));
        holder.last_msg.setText(lastMessage.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("FirebaseId", chatter.getId());
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
        public TextView last_date;
        public TextView last_msg;
//        public ImageView house_image;
//        public TextView not_read;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.chat_list_profile_image);
            nickname = itemView.findViewById(R.id.chat_list_nickname);
            last_date = itemView.findViewById(R.id.chat_list_date);
            last_msg = itemView.findViewById(R.id.chat_list_last_msg);
//            house_image = itemView.findViewById(R.id.chat_list_img_house);
//            not_read = itemView.findViewById(R.id.chat_list_not_read);
        }
    }
}

