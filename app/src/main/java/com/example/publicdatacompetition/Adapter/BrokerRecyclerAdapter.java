package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicdatacompetition.Model.ProvisionalHouse;
import com.example.publicdatacompetition.PermittedHouse;
import com.example.publicdatacompetition.R;

import java.util.ArrayList;

public class BrokerRecyclerAdapter extends RecyclerView.Adapter<BrokerRecyclerAdapter.CustomViewHolder> {

    private ArrayList<ProvisionalHouse> mList;
    private LayoutInflater mInflate;
    private Context context;

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_broker, parent, false);
        BrokerRecyclerAdapter.CustomViewHolder viewHolder = new BrokerRecyclerAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String info = null;
        String pyeong = String.format("%.1f", mList.get(position).getLeaseable_area() / 3.3);
        info = mList.get(position).getDongri() + " · " + pyeong + "평 · " + mList.get(position).getDong() + "동 · "
                + mList.get(position).getHo() + "호";

        holder.tv_item_broker_name.setText(mList.get(position).getResidence_name());
        holder.tv_item_broker_info.setText(info);

        long uk = mList.get(position).getSale_price() / 100000000;
        long man = (mList.get(position).getSale_price() / 10000) % 10000;
        if (mList.get(position).getSale_type().equals("월세")) {
            if (uk > 0) {
                if (man == 0) holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + uk + "억");
                else holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + uk + "억 " + man + "만");
            } else {
                holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + mList.get(position).getMonthly_price() / 10000 + " / " + man + "만");
            }
        } else {
            if (uk > 0) {
                if (man == 0) holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + uk + "억");
                else holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + uk + "억 " + man + "만");
            } else {
                holder.tv_item_broker_price.setText(mList.get(position).getSale_type() + " " + man + "만");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_broker_name, tv_item_broker_info, tv_item_broker_price, tv_item_broker_fee;

        public CustomViewHolder(View view) {
            super(view);
            this.tv_item_broker_name = view.findViewById(R.id.tv_item_broker_name);
            this.tv_item_broker_info = view.findViewById(R.id.tv_item_broker_info);
            this.tv_item_broker_price = view.findViewById(R.id.tv_item_broker_price);
            this.tv_item_broker_fee = view.findViewById(R.id.tv_item_broker_fee);
        }
    }

    public BrokerRecyclerAdapter(Context context, ArrayList<ProvisionalHouse> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

}
