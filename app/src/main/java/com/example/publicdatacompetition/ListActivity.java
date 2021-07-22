package com.example.publicdatacompetition;

import android.content.Intent;
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
    private String mSido;
    private String mSigungu;
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
        addScrollListenerOnRecyclerView();
        addItemTouchListenerOnRecyclerView();
    }

    private void addScrollListenerOnRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mManager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    mToTop.setVisibility(View.VISIBLE);
                }
                else{
                    mToTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addItemTouchListenerOnRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), mRecyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        /*final Intent intent = new Intent(getApplicationContext(),ListDialogActivity.class);
                        intent.putExtra("percent",arr.get(position).getPercent());
                        intent.putExtra("hospitalname",arr.get(position).getHospitalname());
                        intent.putExtra("distance",arr.get(position).getDistance());
                        intent.putExtra("addr",arr.get(position).getAddr());
                        intent.putExtra("sbj",arr.get(position).getSubject());
                        intent.putExtra("pronum",arr.get(position).getPronum());
                        intent.putExtra("totalnum", arr.get(position).getTotalnum());
                        intent.putExtra("tel",arr.get(position).getTel());
                        intent.putExtra("park",arr.get(position).getPark());
                        intent.putExtra("url",arr.get(position).getUrl());
                        intent.putExtra("init_ypos",latitude);
                        intent.putExtra("init_xpos",longitude);
                        intent.putExtra("d_ypos",arr.get(position).getYpos());
                        intent.putExtra("d_xpos",arr.get(position).getXpos());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);*/
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_tomap:
                Intent intent1 = new Intent(getApplicationContext(), MapActivity.class);
                intent1.putExtra("search", mSearch);
                intent1.putExtra("subject", mSubject);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_list_region:
                Intent intent = new Intent(getApplicationContext(), SidoActivity.class);
                intent.putExtra("search", mSearch);
                intent.putExtra("subject", mSubject);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_list_back:
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.iv_list_filter:
                Intent intent2 = new Intent(getApplicationContext(), FilterActivity.class);
                intent2.putExtra("search", mSearch);
                intent2.putExtra("subject", mSubject);
                startActivity(intent2);
                break;
            case R.id.btn_list_search:
                Intent intent3 = new Intent(getApplicationContext(), SearchActivity.class);
                intent3.putExtra("Imfrom", "recyclerview");
                intent3.putExtra("subject", mSubject);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
        }
    }

    private void init() {
        mManager = new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mToMap = findViewById(R.id.fb_tomap);
        mToTop = findViewById(R.id.fb_totop);
        mRegionButton = findViewById(R.id.btn_list_region);
        mSearchButton = findViewById(R.id.btn_list_search);
        mBackButton = findViewById(R.id.btn_list_back);
        mFilterButton = findViewById(R.id.iv_list_filter);
        mItemCount = findViewById(R.id.tv_list_itemcount);
        mToolbarTitle = findViewById(R.id.tv_list_title);

        mRecyclerView.setLayoutManager(mManager);
        mToMap.setOnClickListener(this);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFilterButton.setOnClickListener(this);

        mSido = MyLocation.getInstance().getSIDO();
        mSigungu = MyLocation.getInstance().getSIGUNGU();
        mSubject = getIntent().getStringExtra("subject");
        mSearch = getIntent().getStringExtra("search");

        setToolbarTitle();
    }

    private void setToolbarTitle() {
        mToolbarTitle.setText(mSubject);

        String name;
        if (mSido.length() == 4) {
            name = "" + mSido.charAt(0) + mSido.charAt(2);
        } else {
            name = mSido.substring(0,2);
        }
        if (mSido != null && mSigungu != null) {
            mRegionButton.setText(" " + name + " - " + mSigungu + " ");
        }
    }

}
