package com.example.publicdatacompetition.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.publicdatacompetition.Model.Pictures;
import com.example.publicdatacompetition.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class PagerAdapter_Picture_with_circleindicator extends PagerAdapter {

    private List<String> pictures;
    private LayoutInflater layoutInflater;
    private Context context;

    public PagerAdapter_Picture_with_circleindicator() {

    }

    public PagerAdapter_Picture_with_circleindicator(List<String> mPictures, Context context) {
        this.pictures = mPictures;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_picture_with_circleindicator, container, false);

        ImageView imageView;

        imageView = view.findViewById(R.id.iv_item_picture_with_circle);

        Glide.with(context).load(pictures.get(position)).into(imageView);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
