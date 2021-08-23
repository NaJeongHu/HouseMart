package com.howsmart.housemart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import androidx.viewpager.widget.PagerAdapter;
import com.howsmart.housemart.Model.Pictures;
import com.howsmart.housemart.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        TextView tv_item_explain;
        TextInputEditText textInputEditText;
        TextInputLayout textInputLayout;

        imageView = view.findViewById(R.id.image_picture);
        tv_type = view.findViewById(R.id.tv_picture_type);
        tv_item_explain = view.findViewById(R.id.tv_item_upload_explain);
        textInputEditText = view.findViewById(R.id.edit_item_upload_explain);
        textInputLayout = view.findViewById(R.id.text_input_layout_item_upload_explain);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"팔아요" + position + "번째", Toast.LENGTH_SHORT).show();
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
        //tv_item_explain.setText(pictures.get(position).getType());
        tv_item_explain.setText(pictures.get(position).getTitle());
        //CharSequence cs = "ddd";
        //textInputEditText.setHint(cs);
        //textInputEditText.setHint("testesteset");
        String guide = pictures.get(position).getGuide();
        textInputLayout.setHint(guide);

        if (pictures.get(position).getDescription() != null) {
            textInputEditText.setText(pictures.get(position).getDescription());
        }

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textInputEditText.getText().toString().equals("") || textInputEditText.getText().toString() == null) {

                } else {
                    pictures.get(position).setDescription(textInputEditText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
