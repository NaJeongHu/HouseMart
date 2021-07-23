package com.example.publicdatacompetition.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.viewpager.widget.PagerAdapter;
import com.example.publicdatacompetition.Model.Pictures;
import com.example.publicdatacompetition.R;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;


public class PagerAdapter_Picture extends PagerAdapter {

    private List<Pictures> pictures;
    private LayoutInflater layoutInflater;
    private Context context;

    public PagerAdapter_Picture(List<Pictures> mPictures, Context context) {
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
        View view = layoutInflater.inflate(R.layout.item_picture, container, false);

        ImageView imageView;
        TextView tv_type;

        imageView = view.findViewById(R.id.image_picture);
        tv_type = view.findViewById(R.id.tv_picture_type);

        imageView.setBackgroundResource(pictures.get(position).getImage());
        tv_type.setText(pictures.get(position).getType());


       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0) {
                    Intent intent = new Intent(context, TargetActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);

                }
                else if(position==1){
                    Intent intent = new Intent(context, HomeActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
                else if(position==2){
                    Intent intent = new Intent(context, MartActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
                else if(position==3){
                    Intent intent = new Intent(context, CarActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
            }

        });*/

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
