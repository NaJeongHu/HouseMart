package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.publicdatacompetition.Model.Filter;
import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btn_type1, btn_type2, btn_type3,btn_year_0,btn_year_1,btn_year_5,btn_year_10,btn_year_15,btn_year_15up
            ,btn_park_0,btn_park_1,btn_park_2,btn_filter;
    private LinearLayout linear_month, linear_guarantee, linear_sale;
    private TextView tv_price1, tv_price2, tv_price3, tv_area,tv_reset;
    private RangeSlider slider1, slider2, slider3, slider4;
    private ImageView btn_back;


    private boolean S, M, C;
    private long guarantee_start, guarantee_end, monthly_start, monthly_end, sale_start, sale_end;
    private double area_start, area_end;
    private int year,park;

    private Filter mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();

    }

    public void init() {

        S = false;
        M = true;
        C = false;

        UploadActivity uploadActivity = new UploadActivity();

        btn_back = findViewById(R.id.btn_back);
        btn_filter = findViewById(R.id.btn_filter);
        tv_reset = findViewById(R.id.tv_reset);

        btn_type1 = findViewById(R.id.btn_type1);
        btn_type2 = findViewById(R.id.btn_type2);
        btn_type3 = findViewById(R.id.btn_type3);
        btn_year_0 = findViewById(R.id.btn_year_0);
        btn_year_1 = findViewById(R.id.btn_year_1);
        btn_year_5 = findViewById(R.id.btn_year_5);
        btn_year_10 = findViewById(R.id.btn_year_10);
        btn_year_15 = findViewById(R.id.btn_year_15);
        btn_year_15up = findViewById(R.id.btn_year_15up);
        btn_park_0 = findViewById(R.id.btn_park_0);
        btn_park_1 = findViewById(R.id.btn_park_1);
        btn_park_2 = findViewById(R.id.btn_park_2);

        linear_sale = findViewById(R.id.linear_sale);
        linear_guarantee = findViewById(R.id.linear_guarantee);
        linear_month = findViewById(R.id.linear_month);

        slider1 = findViewById(R.id.slider1);
        slider2 = findViewById(R.id.slider2);
        slider3 = findViewById(R.id.slider3);
        slider4 = findViewById(R.id.slider4);

        tv_price1 = findViewById(R.id.tv_price1);
        tv_price2 = findViewById(R.id.tv_price2);
        tv_price3 = findViewById(R.id.tv_price3);
        tv_area = findViewById(R.id.tv_area);

        btn_type1.setOnClickListener(this);
        btn_type2.setOnClickListener(this);
        btn_type3.setOnClickListener(this);

        btn_year_0.setOnClickListener(this);
        btn_year_1.setOnClickListener(this);
        btn_year_5.setOnClickListener(this);
        btn_year_10.setOnClickListener(this);
        btn_year_15.setOnClickListener(this);
        btn_year_15up.setOnClickListener(this);

        btn_park_0.setOnClickListener(this);
        btn_park_1.setOnClickListener(this);
        btn_park_2.setOnClickListener(this);

        tv_reset.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_filter.setOnClickListener(this);



        slider1.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> thumbs = slider.getValues();
                int st = Math.round(thumbs.get(0));
                int ed = Math.round(thumbs.get(1));
                guarantee_start = cal1(st);
                guarantee_end = cal1(ed);
                tv_price1.setText(uploadActivity.translatePrice(guarantee_start) + "~" + uploadActivity.translatePrice(guarantee_end));
            }
        });

        slider2.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> thumbs = slider.getValues();
                int st = Math.round(thumbs.get(0));
                int ed = Math.round(thumbs.get(1));
                monthly_start = cal2(st);
                monthly_end = cal2(ed);
                tv_price2.setText(uploadActivity.translatePrice(monthly_start) + "~" + uploadActivity.translatePrice(monthly_end));
            }
        });

        slider3.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> thumbs = slider.getValues();
                int st = Math.round(thumbs.get(0));
                int ed = Math.round(thumbs.get(1));
                sale_start = cal3(st);
                sale_end = cal3(ed);
                tv_price3.setText(uploadActivity.translatePrice(sale_start) + "~" + uploadActivity.translatePrice(sale_end));
            }
        });

        slider4.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                String x,y;
                List<Float> thumbs = slider.getValues();
                area_start = Math.round(thumbs.get(0));
                area_end = Math.round(thumbs.get(1));
                if (area_start != 200) {
                    x= ((int) area_start)+"m²("+uploadActivity.translateArea(area_start)+")";
                }
                else{
                    area_start = -1;
                    x = "무제한";
                }
                if (area_end != 200) {
                    y =((int) area_end)+"m²("+uploadActivity.translateArea(area_end)+")";
                }
                else{
                    area_end = -1;
                    y = "무제한";
                }
                tv_area.setText(x + "~" + y);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.btn_filter:
                mFilter = new Filter(S,M,C,guarantee_start,guarantee_end,monthly_start,monthly_end,sale_start,sale_end,area_start,area_end,year,park);
                //todo 리스트 액티비티로 필터의 값 전달
                break;

            case R.id.tv_reset:
                finish();
                startActivity(new Intent(FilterActivity.this, FilterActivity.class));
                break;

            case R.id.btn_type1:
                if (M) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    linear_month.setVisibility(View.GONE);
                    if (!C) {
                        linear_guarantee.setVisibility(View.GONE);
                    }
                    M = false;
                } else {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    linear_month.setVisibility(View.VISIBLE);
                    linear_guarantee.setVisibility(View.VISIBLE);
                    M = true;
                }
                break;

            case R.id.btn_type2:
                if (C) {
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    if (!M) {
                        linear_guarantee.setVisibility(View.GONE);
                    }
                    C = false;
                } else {
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    linear_guarantee.setVisibility(View.VISIBLE);
                    C = true;
                }
                break;

            case R.id.btn_type3:
                if (S) {
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    linear_sale.setVisibility(View.GONE);
                    S = false;
                } else {
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    linear_sale.setVisibility(View.VISIBLE);
                    S = true;
                }
                break;

            case R.id.btn_year_0:
                resetYearButton();
                btn_year_0.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_0.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=0;
                break;
            case R.id.btn_year_1:
                resetYearButton();
                btn_year_1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=1;
                break;
            case R.id.btn_year_5:
                resetYearButton();
                btn_year_5.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_5.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=5;
                break;
            case R.id.btn_year_10:
                resetYearButton();
                btn_year_10.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_10.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=10;
                break;
            case R.id.btn_year_15:
                resetYearButton();
                btn_year_15.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_15.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=15;
                break;
            case R.id.btn_year_15up:
                resetYearButton();
                btn_year_15up.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_year_15up.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                year=-1;
                break;

            case R.id.btn_park_0:
                resetParkButton();
                btn_park_0.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_park_0.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                park=0;
                break;

            case R.id.btn_park_1:
                resetParkButton();
                btn_park_1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_park_1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                park=1;
                break;

            case R.id.btn_park_2:
                resetParkButton();
                btn_park_2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                btn_park_2.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                park=2;
                break;
        }
    }

    private long cal1(int num) {
        long result;
        if (num == 0) result = 0;
        else if (num <= 5) result = num * 100;
        else if (num <= 10) result = 500 + (num - 5) * 500;
        else if (num <= 27) result = 3000 + (num - 10) * 1000;
        else if (num <= 33) result = 20000 + (num - 27) * 5000;
        else if (num <= 39) result = 50000 + (num - 33) * 10000;
        else return -1;
        return result * 10000;
    }

    private long cal2(int num) {
        long result;
        if (num == 0) result = 0;
        else if (num <= 20) result = num * 5;
        else if (num <= 30) result = 100 + (num - 20) * 10;
        else if (num <= 39) result = 200 + (num - 30) * 50;
        else return -1;
        return result * 10000;
    }

    private long cal3(int num) {
        long result;
        if (num == 0) result = 0;
        else if (num <= 5) result = num * 1000;
        else if (num == 6) result = 10000;
        else if (num <= 15) result = 10000 + (num - 6) * 10000;
        else if (num <= 19) result = 100000 + (num - 15) * 50000;
        else return -1;
        return result * 10000;
    }
    private void resetYearButton(){
        btn_year_0.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_0.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_year_1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_year_5.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_5.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_year_10.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_10.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_year_15.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_15.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_year_15up.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_year_15up.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
    }

    private void resetParkButton(){
        btn_park_0.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_park_0.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_park_1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_park_1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
        btn_park_2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
        btn_park_2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
    }
}
