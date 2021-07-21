package com.example.publicdatacompetition;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private String mSIDO;
    private String mSIGUNGU;
    private String mSearch;
    private String mSubject;
    private FloatingActionButton mToMap, mToTop;
    private Button mRegionButton, mSearchButton;
    private ImageView mBackButton, mFilterButton;
    private TextView mItemCount, mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    @Override
    public void onClick(View view) {

    }

    private void init() {
        mToMap = findViewById(R.id.fb_tomap);
        mToTop = findViewById(R.id.fb_totop);
        mRegionButton = findViewById(R.id.btn_list_region);
        mSearchButton = findViewById(R.id.btn_list_search);
        mBackButton = findViewById(R.id.btn_list_back);
        mFilterButton = findViewById(R.id.iv_list_filter);
        mItemCount = findViewById(R.id.tv_list_itemcount);
        mToolbarTitle = findViewById(R.id.tv_list_title);

        mToMap.setOnClickListener(this);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFilterButton.setOnClickListener(this);
    }

}
