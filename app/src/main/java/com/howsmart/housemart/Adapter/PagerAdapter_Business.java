//package com.howsmart.housemart.Adapter;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//
//import com.howsmart.housemart.Fragment_Contract;
//import com.howsmart.housemart.Fragment_Sell;
//
//import java.util.ArrayList;
//
//public class PagerAdapter_Business extends FragmentPagerAdapter {
//
//    private ArrayList<Fragment> items;
//    private ArrayList<String> titles = new ArrayList<String>();
//
//    public PagerAdapter_Business(@NonNull FragmentManager fm) {
//        super(fm);
//        items = new ArrayList<Fragment>();
//        items.add(new Fragment_Sell());
//        items.add(new Fragment_Contract());
//
//        titles.add("판매중인 내역");
//        titles.add("계약중인 내역");
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return items.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles.get(position);
//    }
//}
