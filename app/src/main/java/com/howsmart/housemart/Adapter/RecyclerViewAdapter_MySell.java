package com.howsmart.housemart.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Model.House;
import com.howsmart.housemart.Model.MyHouse;
import com.howsmart.housemart.Model.Realprice;
import com.howsmart.housemart.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_MySell extends RecyclerView.Adapter<RecyclerViewAdapter_MySell.MyViewHolder> {

    private ArrayList<MyHouse> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_state_mysell, tv_date_mysell, tv_name_mysell, tv_list_info_mysell, tv_price_mysell;
        private ImageView iv_mysell;
        private AppCompatButton btn_mysell;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_state_mysell = itemView.findViewById(R.id.tv_state_mysell);
            tv_date_mysell = itemView.findViewById(R.id.tv_date_mysell);
            tv_name_mysell = itemView.findViewById(R.id.tv_name_mysell);
            tv_list_info_mysell = itemView.findViewById(R.id.tv_list_info_mysell);
            tv_price_mysell = itemView.findViewById(R.id.tv_price_mysell);
            iv_mysell = itemView.findViewById(R.id.iv_mysell);
            btn_mysell = itemView.findViewById(R.id.btn_mysell);
            btn_mysell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public RecyclerViewAdapter_MySell(Context context, ArrayList<MyHouse> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflate.inflate(R.layout.item_mysell, parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()) {
            MyHouse house = new MyHouse();
            house = mList.get(position);
            if (house != null) {
                holder.tv_state_mysell.setText(translateState(house.getOfferState()));
                holder.tv_date_mysell.setText("등록일 " + translateDate(house.getCreateDate()));
                holder.tv_name_mysell.setText(house.getResidence_name());
                String pyeong = String.format("%.1f", mList.get(position).getLeaseable_area() / 3.3);
                String info = house.getDongri() + " " + pyeong + "평 " + house.getDong() + "동 " + house.getHo() + "호";
                holder.tv_list_info_mysell.setText(info);
                String type = house.getSale_type();
                if (type.equals("월세")) {
                    holder.tv_price_mysell.setText(type + " " + translatePrice(house.getSale_price()) + "/" + translatePrice(house.getMonthly_price()));
                } else {
                    holder.tv_price_mysell.setText(type + " " + translatePrice(house.getSale_price()));
                }
                Glide.with(context).load(house.getTitleImg()).into(holder.iv_mysell);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public String translatePrice(Long price) {

        String a = "", b = "", c = "";
        if (price == 0) {
            return "0원";
        }
        if (price == -1) {
            return "무제한";
        }
        if (price >= 100000000) {
            Long price1 = price / 100000000;
            price %= 100000000;
            a = price1 + "억";
        }
        if (price >= 10000) {
            Long price2 = price / 10000;
            price %= 10000;
            b = " " + price2 + "만";
        }
        if (price > 0) {
            c = " " + price.toString();
        }
        return a + b + c + "원";
    }

    public String translateDate(String date) {

        String a = "", b = "", c = "";
        a = date.substring(2, 4);
        b = date.substring(4, 6);
        c = date.substring(6);
        return a + "." + b + "." + c;
    }

    public String translateArea(String area) {
        Double temp = Double.parseDouble(area);
        return String.format("%.1f m²", temp);
    }

    public String translateState(String state) {
        String ret;
        if (state.equals("REJECTED")) {
            ret = "등록불가매물";
        } else if (state.equals("UNRELIABLE")) {
            ret = "승인대기중";
        } else if (state.equals("RELIABLE")) {
            ret = "판매중";
        } else if (state.equals("SOLD_OUT")) {
            ret = "판매 완료";
        } else {
            ret = "계약중";
        }
        return ret;
    }
}
