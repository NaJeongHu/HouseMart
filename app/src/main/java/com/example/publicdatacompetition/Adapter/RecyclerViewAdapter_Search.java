package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicdatacompetition.Model.item_search_name;
import com.example.publicdatacompetition.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Search extends RecyclerView.Adapter<RecyclerViewAdapter_Search.MyViewHolder> {

    private ArrayList<item_search_name> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;
    private String name;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_apartname;
        private TextView tv_apartaddress;
        private View line_search;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_apartaddress = itemView.findViewById(R.id.tv_apartaddress);
            tv_apartname = itemView.findViewById(R.id.tv_apartname);
            line_search = itemView.findViewById(R.id.line_search);
        }

    }

    public RecyclerViewAdapter_Search(Context context, ArrayList<item_search_name> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflate.inflate(R.layout.item_search_upload, parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()) {
            item_search_name temp = new item_search_name();
            temp = mList.get(position);
            name = temp.getName();
            SpannableStringBuilder builder = new SpannableStringBuilder(name);
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#2265DA")), temp.getStart(),  temp.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_apartname.setText(builder);
            holder.tv_apartaddress.setText(temp.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
