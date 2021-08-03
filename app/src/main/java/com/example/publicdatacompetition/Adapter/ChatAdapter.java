package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.publicdatacompetition.Model.Chat;
import com.example.publicdatacompetition.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final String TAG = "ChatAdapter";

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;

    public ChatAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_left, parent, false);
            return new ChatAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat_right, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        Log.d("ChatAdapter", "chat: " + chat.toString());

        String str_date = chat.getTimestamp();

        String date = str_date.substring(0, 14);
        String time = str_date.substring(14, 22);

        if(position == 0 || position-1 > 0 && !mChat.get(position-1).getTimestamp().substring(0, 14).equals(date)){
            holder.date_layout.setVisibility(View.VISIBLE);
        } else {
            holder.date_layout.setVisibility(View.GONE);
        }

        holder.txt_date.setText(date);
        holder.txt_time.setText(time);
        holder.message.setText(chat.getMessage());
        Log.d(TAG, "imageurl: " + imageurl);
        Log.d(TAG, "imageurl.equals(\"default\")" + imageurl.equals("default"));
        if(imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.drawable.preview);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if(!chat.isIsseen()) {
            holder.txt_seen.setText("1");
        } else {
            holder.txt_seen.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout date_layout;
        public TextView txt_date;
        public TextView txt_time;
        public TextView message;
        public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_layout = itemView.findViewById(R.id.chat_item_date_relative_layout);
            txt_date = itemView.findViewById(R.id.chat_item_date);
            txt_time = itemView.findViewById(R.id.chat_item_time);
            message = itemView.findViewById(R.id.chat_item_show_message);
            profile_image = itemView.findViewById(R.id.chat_item_profile_image);
            txt_seen = itemView.findViewById(R.id.chat_item_txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else{
            return MSG_TYPE_LEFT;
        }
    }

    public void addChat(Chat chat){
        mChat.add(chat);
    }

    public void removeChat(Chat chat){
        mChat.remove(chat);
    }
}