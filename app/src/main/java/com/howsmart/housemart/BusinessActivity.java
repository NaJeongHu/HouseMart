package com.howsmart.housemart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.howsmart.housemart.Model.User;

import java.util.ArrayList;

public class BusinessActivity extends AppCompatActivity {

    Fragment fragment_sell, fragment_contract;
    private ImageView btn_back_business;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        btn_back_business = findViewById(R.id.btn_back_business);
        btn_back_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUser = (User) getIntent().getSerializableExtra("user");

        Bundle bundle = new Bundle();
        bundle.putString("userId",mUser.getUserId());


        ViewPager viewPager = findViewById(R.id.viewpager_business);
        PagerAdapter_Business adapter = new PagerAdapter_Business(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tab_business);
        tabs.setupWithViewPager(viewPager);
    }

    public class PagerAdapter_Business extends FragmentPagerAdapter {

        private ArrayList<String> titles = new ArrayList<String>();

        public PagerAdapter_Business(@NonNull FragmentManager fm) {
            super(fm);

            titles.add("판매중인 내역");
            titles.add("계약중인 내역");
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                Fragment_Sell fragment_sell = new Fragment_Sell();
                fragment_sell.setUserId(mUser.getUserId());
                return fragment_sell;
            }
            else {
                Fragment_Contract fragment_contract = new Fragment_Contract();
                fragment_contract.setUserId(mUser.getUserId());
                return fragment_contract;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}