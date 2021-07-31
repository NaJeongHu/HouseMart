package com.example.publicdatacompetition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.publicdatacompetition.Adapter.RecyclerViewAdapter_Search;
import com.example.publicdatacompetition.Model.item_search_name;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity_upload extends AppCompatActivity {

    ArrayList<item_search_name> arr = null;
    private RecyclerViewAdapter_Search adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private String search;

    private EditText edit_search;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_upload);
        init();
        renewlist();

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = edit_search.getText().toString();
                renewlist();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void init() {
        recyclerView = findViewById(R.id.recycler_search_upload);
        manager = new LinearLayoutManager(SearchActivity_upload.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        edit_search = findViewById(R.id.edit_search);

        btn_back = findViewById(R.id.btn_back_search_upload);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), recyclerView, new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String code = arr.get(position).getCode();
                String name = arr.get(position).getName();
                String address = arr.get(position).getAddress();
                String sido = arr.get(position).getSido();
                String sigungoo = arr.get(position).getSigungoo();
                String dongri = arr.get(position).getDongri();
                String date = arr.get(position).getDate();
                Integer allnumber = arr.get(position).getAllnumber();
                Integer parkingnumber = arr.get(position).getParkingnumber();
                String contact = arr.get(position).getContact();

                Intent data = new Intent();

                data.putExtra("sido",sido);
                data.putExtra("sigungoo",sigungoo);
                data.putExtra("dongri",dongri);
                data.putExtra("date",date);
                data.putExtra("allnumber",allnumber);
                data.putExtra("parkingnumber",parkingnumber);
                data.putExtra("contact",contact);
                data.putExtra("code", code);
                data.putExtra("name", name);
                data.putExtra("address", address);
                setResult(RESULT_OK,data);
                finish();
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        }));
    }

    private void renewlist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                arr = new ArrayList<>();
                readDataFromCsv();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new RecyclerViewAdapter_Search(getApplicationContext(), arr);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void readDataFromCsv() {
        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.apartment));
        CSVReader reader = new CSVReader(is);
        List<String[]> list = null;
        try {
            list = reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (arr.size() != 0) {
            arr.clear();
        }
        Log.d("after_clear", arr.size() + "");

        for (String[] str : list) {

            item_search_name entity = new item_search_name();
            entity.setSido(str[0]);
            entity.setSigungoo(str[1]);
            entity.setDongri(str[3]);
            entity.setCode(str[4]);
            entity.setName(str[5]);
            entity.setAddress(str[8]);
            entity.setDate(str[10]);
            entity.setAllnumber(Integer.parseInt(str[12]));
            entity.setParkingnumber(Integer.parseInt(str[43]));
            entity.setContact(str[49]);

            if (search != null) {
                int start = entity.getName().indexOf(search); //entity 이름에서 search가 처음 등장하는 idx 반환
                if (start!=-1) {  // 검색어가 포함된 경우
                    entity.setStart(start);
                    entity.setEnd(search.length()+start);
                    arr.add(entity); //entity 이름이 search를 포함하기 때문에 arr에 추가
                }
            } else {
                adapter = null; //검색하는 것이 없다면 adapter을 null로 만드나??
            }
        }
    }


}