package com.howsmart.housemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.howsmart.housemart.Model.Contract;
import com.howsmart.housemart.Model.MyContract;
import com.howsmart.housemart.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_MyContract extends RecyclerView.Adapter<RecyclerViewAdapter_MyContract.MyViewHolder> {

    private ArrayList<MyContract> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_state_mycontract, tv_date_mycontract, tv_name_mycontract, tv_list_info_mycontract, tv_price_mycontract,tv_name1_mycontract,tv_name2_mycontract;
        private ImageView iv_mycontract;
        private AppCompatButton btn_mycontract;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_state_mycontract = itemView.findViewById(R.id.tv_state_mycontract);
            tv_date_mycontract = itemView.findViewById(R.id.tv_date_mycontract);
            tv_name_mycontract = itemView.findViewById(R.id.tv_name_mycontract);
            tv_list_info_mycontract = itemView.findViewById(R.id.tv_list_info_mycontract);
            tv_price_mycontract = itemView.findViewById(R.id.tv_price_mycontract);
            tv_name1_mycontract = itemView.findViewById(R.id.tv_name1_mycontract);
            tv_name2_mycontract = itemView.findViewById(R.id.tv_name2_mycontract);
            iv_mycontract = itemView.findViewById(R.id.iv_mycontract);
            btn_mycontract = itemView.findViewById(R.id.btn_mycontract);
            btn_mycontract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    public RecyclerViewAdapter_MyContract(Context context, ArrayList<MyContract> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflate.inflate(R.layout.item_mycontract, parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()) {
            MyContract contract = new MyContract();
            contract = mList.get(position);
            if (contract != null) {
                holder.tv_state_mycontract.setText(translateState(contract.getOfferState()));
                holder.tv_date_mycontract.setText("등록일 " + translateDate(contract.getCreateDate()));
                holder.tv_name_mycontract.setText(contract.getResidence_name());
                String pyeong = String.format("%.1f", contract.getLeaseable_area() / 3.3);
                String info = contract.getDongri() + " " + pyeong + "평 " + contract.getDong() + "동 " + contract.getHo() + "호";
                holder.tv_list_info_mycontract.setText(info);
                String type = contract.getSale_type();
                if (type.equals("월세")) {
                    holder.tv_price_mycontract.setText(type + " " + translatePrice(contract.getSale_price()) + "/" + translatePrice(contract.getMonthly_price()));
                } else {
                    holder.tv_price_mycontract.setText(type + " " + translatePrice(contract.getSale_price()));
                }
                holder.tv_name1_mycontract.setText(contract.getSellerName());
                holder.tv_name2_mycontract.setText(contract.getBuyerName());
                Glide.with(context).load(contract.getTitleImg()).into(holder.iv_mycontract);
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
            ret = "계약 완료";
        } else {
            ret = "계약중";
        }
        return ret;
    }
}
