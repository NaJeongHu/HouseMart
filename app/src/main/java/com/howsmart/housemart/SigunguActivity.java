package com.howsmart.housemart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SigunguActivity extends AppCompatActivity {

    private ListView Lv_gu;
    private TextView Tv_precity;
    private String sido;
    private String subject;
    private String search;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigungu);

        Intent intent = getIntent();
        sido = intent.getStringExtra("sido");

        init();

        subject = "";
        search = "";
        from = "";
        subject = intent.getStringExtra("subject");
        search = intent.getStringExtra("search");
        from = intent.getStringExtra("from");

        Lv_gu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                MyLocation.getInstance().setSIDO(sido);
                MyLocation.getInstance().setSIGUNGU((String) Lv_gu.getAdapter().getItem(position));

                if (from.equals("BrokerActivity")) {
                    Intent intent = new Intent(getApplicationContext(), BrokerActivity.class);
                    intent.putExtra("search", search);
                    intent.putExtra("subject", subject);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    // 시도, 시군구 저장하니까 intent에 put할 필요 없음
                    //intent.putExtra("sido", sido);
                    //intent.putExtra("gu_name", (String) Lv_gu.getAdapter().getItem(position)); /*송신*/
                    intent.putExtra("search", search);
                    intent.putExtra("subject", subject);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });
    }

    private void init() {
        Lv_gu = (ListView) findViewById(R.id.lv_gu);
        Tv_precity = (TextView) findViewById(R.id.tv_precity);

        String[] gu;
        if(sido.equals("서울특별시")) {
            gu = getResources().getStringArray(R.array.seoul_middle);
        }else if(sido.equals("경기도")){
            gu = getResources().getStringArray(R.array.geunggi_middle);
        }else if(sido.equals("부산광역시")){
            gu = getResources().getStringArray(R.array.busan_middle);
        }else if(sido.equals("인천광역시")){
            gu = getResources().getStringArray(R.array.incheon_middle);
        }else if(sido.equals("대구광역시")){
            gu = getResources().getStringArray(R.array.daegu_middle);
        }else if(sido.equals("대전광역시")){
            gu = getResources().getStringArray(R.array.daejeun_middle);
        }else if(sido.equals("광주광역시")){
            gu = getResources().getStringArray(R.array.gangju_middle);
        }else if(sido.equals("울산광역시")){
            gu = getResources().getStringArray(R.array.ulsan_middle);
        }else if(sido.equals("경상남도")){
            gu = getResources().getStringArray(R.array.geungnam_middle);
        }else if(sido.equals("경상북도")){
            gu = getResources().getStringArray(R.array.geungbook_middle);
        }else if(sido.equals("전라남도")){
            gu = getResources().getStringArray(R.array.junnam_midlle);
        }else if(sido.equals("전라북도")){
            gu = getResources().getStringArray(R.array.junbook_middle);
        }else if(sido.equals("충청남도")){
            gu = getResources().getStringArray(R.array.choongnam_middle);
        }else if(sido.equals("충청북도")){
            gu = getResources().getStringArray(R.array.choongbook_middle);
        }else if(sido.equals("강원도")){
            gu = getResources().getStringArray(R.array.gangwon_middle);
        }else if(sido.equals("세종특별자치시")){
            gu = getResources().getStringArray(R.array.sejong_middle);
        }else{
            gu = getResources().getStringArray(R.array.jeaju_middle);
        }

        Tv_precity.setText(sido);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gu);

        Lv_gu.setAdapter(adapter);
    }
}