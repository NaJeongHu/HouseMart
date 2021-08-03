package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class SidoActivity extends AppCompatActivity {

    private ListView Lv_city;
    private String subject;
    private String search;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sido);

        init();

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        search = intent.getStringExtra("search");
        from = intent.getStringExtra("from");

        Lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SigunguActivity.class);

                intent.putExtra("sido", (String) Lv_city.getAdapter().getItem(position)); /*송신*/
                intent.putExtra("subject", subject);
                intent.putExtra("search", search);
                intent.putExtra("from", from);

                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

    }

    private void init() {
        Lv_city = (ListView) findViewById(R.id.lv_city);
        String[] city = getResources().getStringArray(R.array.array_city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city);
        Lv_city.setAdapter(adapter);
        subject = "";
        search = "";
        from = "";
    }

}