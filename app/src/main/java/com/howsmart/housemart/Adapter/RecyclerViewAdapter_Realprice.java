package com.howsmart.housemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.howsmart.housemart.Model.Realprice;
import com.howsmart.housemart.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Realprice extends RecyclerView.Adapter<RecyclerViewAdapter_Realprice.MyViewHolder> {

    private ArrayList<Realprice> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_date_realprice, tv_type_realprice, tv_price_realprice, tv_area_realprice, tv_floor_realprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date_realprice = itemView.findViewById(R.id.tv_date_realprice);
            tv_type_realprice = itemView.findViewById(R.id.tv_type_realprice);
            tv_price_realprice = itemView.findViewById(R.id.tv_price_realprice);
            tv_area_realprice = itemView.findViewById(R.id.tv_area_realprice);
            tv_floor_realprice = itemView.findViewById(R.id.tv_floor_realprice);
        }

    }

    public RecyclerViewAdapter_Realprice(Context context, ArrayList<Realprice> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflate.inflate(R.layout.item_realprice_upload, parent, false);
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()) {
            Realprice temp = new Realprice();
            temp = mList.get(position);
            if (temp != null) {
                holder.tv_date_realprice.setText(translateDate(temp.getContract_date()));
                holder.tv_type_realprice.setText(temp.getSale_type());
                holder.tv_area_realprice.setText(translateArea(temp.getNet_leaseable_area()));
                if (temp.getSale_type().equals("월세")) {
                    holder.tv_price_realprice.setText(translatePrice(temp.getSale_price()) + "/" + temp.getMonthly_price());
                } else {
                    holder.tv_price_realprice.setText(translatePrice(temp.getSale_price()));
                }
                holder.tv_floor_realprice.setText(temp.getFloor());
            }
            ;
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public String translatePrice(String price) {

        String a = "", b = "", c = "";
        a = price.substring(0, price.indexOf(","));
        b = price.substring(price.indexOf(",") + 1) + "만";
        if (a.length() >= 2) {
            c = a.substring(0, a.length() - 1) + "억";
            a = " " + a.substring(a.length() - 1);
        }
        if (a.equals("0") && b.equals("000")) {
            return c + "원";
        }
        return c + a + b + "원";
    }

    public String translateDate(String date) {

        String a = "", b = "", c = "";
        a = date.substring(2, 4);
        b = date.substring(4, 6);
        c = date.substring(6);
        return a + "." + b + "." + c;
    }
    public String translateArea(String area){
        Double temp = Double.parseDouble(area);
        return String.format("%.1f m²",temp);
    }
}
