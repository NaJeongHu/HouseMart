package com.example.publicdatacompetition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.CustomViewHolder> {

    private ArrayList<PermittedHouse> mList;
    private LayoutInflater mInflate;
    private Context context;

    public ListRecyclerAdapter(Context context, ArrayList<PermittedHouse> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_list, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerAdapter.CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView residence_name, info, sale_price, titleImg;

        public CustomViewHolder(View view) {
            super(view);
            this.residence_name = view.findViewById(R.id.tv_item_list_name);
            this.info = view.findViewById(R.id.tv_item_list_info);
        }
    }

}
