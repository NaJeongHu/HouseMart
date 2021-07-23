package com.example.publicdatacompetition;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.publicdatacompetition.Adapter.PagerAdapter_Picture;
import com.example.publicdatacompetition.Model.Pictures;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener, PagerAdapter_Picture.OnItemClick {

    private ImageView btn_back;
    private AppCompatButton btn_type1, btn_type2, btn_type3,btn_no_dialog_upload,btn_okay_dialog_upload;
    private LinearLayout lin_price_month;
    private TextInputEditText edit_apartment, edit_dong, edit_ho, edit_area1, edit_area2, edit_price_all, edit_price_month, edit_price_manage,edit_introduce_short,edit_introduce_long;
    private TextView tv_area1, tv_area2, tv_ratio1, tv_ratio2, tv_price_ratio1, tv_price_ratio2, tv_price_type, tv_price_all, tv_price_month, tv_price_manage,tv_complete;
    private RangeSlider slider_ratio1, slider_ratio2;
    private CheckBox chk_nego, chk_door, chk_air, chk_ref, chk_kimchi, chk_closet;

    private long price_first, price_second, price_third,price_all,price_zero;
    private Boolean bool_type1, bool_type2, bool_type3, bool_nego, bool_door, bool_air, bool_ref, bool_kimchi, bool_closet;
    private String apartment;
    private int dong,ho;
    private String introduce_short;
    private String introduce_long;
    private int picture_clicked_position = 0;

    private PagerAdapter_Picture pagerAdapter_picture;

    ViewPager viewPager;
    PagerAdapter_Picture adapter;
    List<Pictures> models;

    private static final int IMAGE_REQUEST = 0;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        init();
        models = new ArrayList<>();
        models.add(new Pictures("아파트 외관 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("현관 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("거실 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("베란다 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("주방 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("방1 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("방2 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("방3 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("방4 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("화장실1 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("화장실2 사진을 등록해주세요",R.drawable.preview));
        models.add(new Pictures("화장실3 사진을 등록해주세요",R.drawable.preview));

        pagerAdapter_picture = new PagerAdapter_Picture();

        adapter = new PagerAdapter_Picture(models,this,this);

        viewPager = findViewById(R.id.viewPager_upload_picture);
        int dpValue = 54;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/3);

        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        edit_dong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edit_dong.getText().toString().equals("") || edit_dong.getText().toString() == null){

                }
                else{
                    dong = Integer.parseInt(edit_dong.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_ho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edit_ho.getText().toString().equals("") || edit_ho.getText().toString() == null){

                }
                else{
                    ho = Integer.parseInt(edit_ho.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_introduce_short.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edit_introduce_short.getText().toString().equals("") || edit_introduce_short.getText().toString() == null){

                }
                else{
                    introduce_short = edit_introduce_short.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_introduce_long.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edit_introduce_long.getText().toString().equals("") || edit_introduce_long.getText().toString() == null){

                }
                else{
                    introduce_long = edit_introduce_long.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_area1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area1.getText().toString().equals("") || edit_area1.getText().toString() == null) {
                    tv_area1.setText("평수");
                } else {
                    try {
                        Double area = Double.parseDouble(edit_area1.getText().toString().trim());
                        tv_area1.setText(translateArea(area));
                    } catch (NumberFormatException e) {
                        tv_area1.setText("평수");
                        Toast.makeText(getBaseContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_area2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_area2.getText().toString().equals("") || edit_area2.getText().toString() == null) {
                    tv_area2.setText("평수");
                } else {
                    try {
                        Double area = Double.parseDouble(edit_area2.getText().toString().trim());
                        tv_area2.setText(translateArea(area));
                    } catch (NumberFormatException e) {
                        tv_area2.setText("평수");
                        Toast.makeText(getBaseContext(), "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_price_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_all.getText().toString().equals("") || edit_price_all.getText().toString() == null) {
                    tv_price_all.setText("가격");
                } else {
                    price_all = Long.parseLong(edit_price_all.getText().toString().trim());
                    tv_price_all.setText(translatePrice(price_all));
                    tv_price_ratio1.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_month.getText().toString().equals("") || edit_price_month.getText().toString() == null) {
                    tv_price_month.setText("가격");
                } else {
                    Long price = Long.parseLong(edit_price_month.getText().toString().trim());
                    tv_price_month.setText(translatePrice(price));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_price_manage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit_price_manage.getText().toString().equals("") || edit_price_manage.getText().toString() == null) {
                    tv_price_manage.setText("가격");
                } else {
                    Long price = Long.parseLong(edit_price_manage.getText().toString().trim());
                    tv_price_manage.setText(translatePrice(price));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        slider_ratio1.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (price_all == -1){
                    tv_price_ratio1.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
                else {
                    List<Float> thumbs = slider.getValues();
                    price_first = Math.round(thumbs.get(0));
                    price_second = Math.round(thumbs.get(1));
                    price_second = price_second - price_first;
                    price_third = 100 - price_first - price_second;
                    tv_ratio1.setText(price_first + "% : " + price_second + "% : " + price_third+"%");
                    price_all = Long.parseLong(edit_price_all.getText().toString().trim());
                    price_first = price_all * price_first / 100;
                    price_second = price_all * price_second / 100;
                    price_third = price_all * price_third / 100;
                    tv_price_ratio1.setText("계약금 : "+translatePrice(price_first) + "\n중도금 : " + translatePrice(price_second) + "\n    잔금 : " + translatePrice(price_third));
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
            }
        });
        slider_ratio2.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                if (price_first==-1){
                    tv_price_ratio2.setText("슬라이더를 움직여서 상세금액을 확인해주세요");
                }
                else {
                    List<Float> thumbs = slider.getValues();
                    long first = Math.round(thumbs.get(0));
                    long second = 100 - first;
                    tv_ratio2.setText(first + "% : " + second+"%");
                    first = price_first * first / 100;
                    second = price_first * second / 100;
                    tv_price_ratio2.setText("        가계약금 : "+translatePrice(first) + "\n나머지 계약금 : " + translatePrice(second));
                }
            }
        });

    }

    private void init() {
        btn_back = findViewById(R.id.btn_back_upload);

        btn_type1 = findViewById(R.id.btn_type1);
        btn_type2 = findViewById(R.id.btn_type2);
        btn_type3 = findViewById(R.id.btn_type3);

        lin_price_month = findViewById(R.id.lin_price_month);

        edit_apartment = findViewById(R.id.edit_apartment);
        edit_dong = findViewById(R.id.edit_dong);
        edit_ho = findViewById(R.id.edit_ho);
        edit_area1 = findViewById(R.id.edit_area1);
        edit_area2 = findViewById(R.id.edit_area2);
        edit_price_all = findViewById(R.id.edit_price_all);
        edit_price_month = findViewById(R.id.edit_price_month);
        edit_price_manage = findViewById(R.id.edit_price_manage);
        edit_introduce_short = findViewById(R.id.edit_introduce_short);
        edit_introduce_long = findViewById(R.id.edit_introduce_long);

        tv_area1 = findViewById(R.id.tv_area1);
        tv_area2 = findViewById(R.id.tv_area2);
        tv_ratio1 = findViewById(R.id.tv_ratio1);
        tv_ratio2 = findViewById(R.id.tv_ratio2);
        tv_price_ratio1 = findViewById(R.id.tv_price_ratio1);
        tv_price_ratio2 = findViewById(R.id.tv_price_ratio2);
        tv_price_type = findViewById(R.id.tv_price_type);
        tv_price_all = findViewById(R.id.tv_price_all);
        tv_price_month = findViewById(R.id.tv_price_month);
        tv_price_manage = findViewById(R.id.tv_price_manage);
        tv_complete = findViewById(R.id.tv_complete);

        slider_ratio1 = findViewById(R.id.slider_ratio1);
        slider_ratio2 = findViewById(R.id.slider_ratio2);

        chk_nego = findViewById(R.id.chk_nego);
        chk_door = findViewById(R.id.chk_door);
        chk_air = findViewById(R.id.chk_air);
        chk_ref = findViewById(R.id.chk_ref);
        chk_kimchi = findViewById(R.id.chk_kimchi);
        chk_closet = findViewById(R.id.chk_closet);

        btn_back.setOnClickListener(this);
        btn_type1.setOnClickListener(this);
        btn_type2.setOnClickListener(this);
        btn_type3.setOnClickListener(this);
        chk_nego.setOnClickListener(this);
        chk_door.setOnClickListener(this);
        chk_air.setOnClickListener(this);
        chk_ref.setOnClickListener(this);
        chk_kimchi.setOnClickListener(this);
        chk_closet.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        bool_type1 = true;
        bool_type2 = false;
        bool_type3 = false;
        bool_nego = false;
        bool_air = false;
        bool_door = false;
        bool_ref = false;
        bool_kimchi = false;
        bool_closet = false;

        price_first = -1;
        price_all = -1;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_upload:
                onBackPressed();
                break;

            case R.id.btn_type1:
                if (!bool_type1) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.VISIBLE);
                    tv_price_type.setText("보증금");
                    bool_type1 = true;
                    bool_type2 = false;
                    bool_type3 = false;
                }
                break;

            case R.id.btn_type2:
                if (!bool_type2) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("보증금");
                    bool_type1 = false;
                    bool_type2 = true;
                    bool_type3 = false;
                }
                break;

            case R.id.btn_type3:
                if (!bool_type3) {
                    btn_type1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type1.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_whitegray));
                    btn_type2.setTextColor(AppCompatResources.getColorStateList(this, R.color.black));
                    btn_type3.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_round_blue));
                    btn_type3.setTextColor(AppCompatResources.getColorStateList(this, R.color.white));
                    lin_price_month.setVisibility(View.GONE);
                    tv_price_type.setText("매매가");
                    bool_type1 = false;
                    bool_type2 = false;
                    bool_type3 = true;
                }
                break;

            case R.id.chk_nego:
                if (chk_nego.isChecked()) {
                    bool_nego = true;
                } else {
                    bool_nego = false;
                }
                break;
            case R.id.chk_door:
                if (chk_door.isChecked()) {
                    bool_door = true;
                } else {
                    bool_door = false;
                }
                break;
            case R.id.chk_air:
                if (chk_air.isChecked()) {
                    bool_air = true;
                } else {
                    bool_air = false;
                }
                break;
            case R.id.chk_ref:
                if (chk_ref.isChecked()) {
                    bool_ref = true;
                } else {
                    bool_ref = false;
                }
                break;
            case R.id.chk_kimchi:
                if (chk_kimchi.isChecked()) {
                    bool_kimchi = true;
                } else {
                    bool_kimchi = false;
                }
                break;
            case R.id.chk_closet:
                if (chk_closet.isChecked()) {
                    bool_closet = true;
                } else {
                    bool_closet = false;
                }
                break;

            case R.id.tv_complete:
                upload_dialog(view);
                break;
        }
    }

    public String translatePrice(Long price) {

        String a = "", b = "", c = "";

        if (price >= 100000000) {
            Long price1 = price / 100000000;
            price %= 100000000;
            a = price1 + "억 ";
        }
        if (price >= 10000) {
            Long price2 = price / 10000;
            price %= 10000;
            b = price2 + "만";
        }
        if (price > 0) {
            c = " " + price.toString();
        }

        return a + b + c + "원";
    }

    public String translateArea(Double area) {
        area *= 0.3025;
        area = Math.round(area * 10) / 10.0;
        return area.toString() + " 평";
    }

    public void upload_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_upload, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.btn_okay_dialog_upload);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                // todo : retrofit

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        Button cancle_btn = dialogView.findViewById(R.id.btn_no_dialog_upload);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST) {
            if(resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();

                //set Image to mIvPicture
                if (imageUri != null) {
                    models.get(picture_clicked_position).setUri(imageUri);
                    viewPager.setAdapter(adapter);
                    //mIvPicture.setImageURI(imageUri);
                }
            } else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(int value) {
        picture_clicked_position = value;
    }
}
