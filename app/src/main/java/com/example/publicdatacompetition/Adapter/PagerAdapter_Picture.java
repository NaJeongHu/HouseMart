package com.example.publicdatacompetition.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import androidx.viewpager.widget.PagerAdapter;
import com.example.publicdatacompetition.Model.Pictures;
import com.example.publicdatacompetition.R;
import com.example.publicdatacompetition.UploadActivity;

import java.io.InputStream;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class PagerAdapter_Picture extends PagerAdapter {

    private List<Pictures> pictures;
    private LayoutInflater layoutInflater;
    private Context context;
    private static final int IMAGE_REQUEST = 0;
    private int ClickedPosition = 0;
    private OnItemClick mCallback;

    public int getClickedPosition() {
        return ClickedPosition;
    }

    public void setClickedPosition(int clickedPosition) {
        ClickedPosition = clickedPosition;
    }


    public PagerAdapter_Picture() {

    }

    public PagerAdapter_Picture(List<Pictures> mPictures, Context context, OnItemClick listener) {
        this.pictures = mPictures;
        this.context = context;
        this.mCallback = listener;
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
        View view = layoutInflater.inflate(R.layout.item_picture, container, false);

        ImageView imageView;
        TextView tv_type;

        imageView = view.findViewById(R.id.image_picture);
        tv_type = view.findViewById(R.id.tv_picture_type);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"팔아요" + position + "번째", Toast.LENGTH_SHORT).show();
                mCallback.onClick(position);
                ClickedPosition = position;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ((Activity) context).startActivityForResult(intent,IMAGE_REQUEST);
                //context.startActivity(intent);
            }
        });
        if (pictures.get(position).getUri() != null) {
            imageView.setImageURI(pictures.get(position).getUri());
        } else {
            imageView.setBackgroundResource(pictures.get(position).getImage());
        }

        //imageView.setBackgroundResource(pictures.get(position).getImage());
        tv_type.setText(pictures.get(position).getType());




        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public interface OnItemClick {
        void onClick (int value);
    }
}
