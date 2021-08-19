package com.howsmart.housemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class BusinessActivity extends AppCompatActivity {

    Fragment fragment_sell, fragment_contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        fragment_sell = new Fragment_Sell();
        fragment_contract = new Fragment_Contract();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_business, fragment_sell).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tab_business);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0) {
                    selected = fragment_sell;
                } else {
                    selected = fragment_contract;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_business, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}