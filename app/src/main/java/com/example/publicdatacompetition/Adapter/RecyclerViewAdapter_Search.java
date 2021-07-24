package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicdatacompetition.Model.item_search_name;
import com.example.publicdatacompetition.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Search extends RecyclerView.Adapter<RecyclerViewAdapter_Search.MyViewHolder>{

    private ArrayList<item_search_name> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

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

        View view = mInflate.inflate(R.layout.item_search_upload, parent, false) ;
        MyViewHolder vh = new MyViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_apartaddress.setText(mList.get(position).getAddress());
        holder.tv_apartname.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }



}
