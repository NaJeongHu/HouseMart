package com.example.publicdatacompetition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicdatacompetition.Model.Filter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private String mSido;
    private String mSigungu;
    private String mSearch;
    private String mSubject;
    private FloatingActionButton mToTop;
    private Button mRegionButton, mSearchButton;
    private ImageView mBackButton, mFilterButton;
    private TextView mItemCount, mToolbarTitle;
    private ArrayList<PermittedHouse> PermittedList;
    private ArrayList<PermittedHouse> PermittedList_filterd;
    private ListRecyclerAdapter adapter;
    private Filter mFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        addScrollListenerOnRecyclerView();
        addItemTouchListenerOnRecyclerView();
        getDataFromServer();
    }


    // when user scroll recycler
    private void addScrollListenerOnRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = mManager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    mToTop.setVisibility(View.VISIBLE);
                } else {
                    mToTop.setVisibility(View.GONE);
                }
            }
        });
    }

    // when user touch item
    private void addItemTouchListenerOnRecyclerView() {
        mRecyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), mRecyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), HouseInfoActivity.class);
                        intent.putExtra("idx",PermittedList.get(position).getIdx());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        mManager = new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mToTop = findViewById(R.id.fb_totop);
        mRegionButton = findViewById(R.id.btn_list_region);
        mSearchButton = findViewById(R.id.btn_list_search);
        mBackButton = findViewById(R.id.btn_list_back);
        mFilterButton = findViewById(R.id.iv_list_filter);
        mItemCount = findViewById(R.id.tv_list_itemcount);
        mToolbarTitle = findViewById(R.id.tv_list_title);

        mRecyclerView.setLayoutManager(mManager);
        mToTop.setOnClickListener(this);
        mRegionButton.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mFilterButton.setOnClickListener(this);

        mSido = MyLocation.getInstance().getSIDO();
        mSigungu = MyLocation.getInstance().getSIGUNGU();
        mSubject = getIntent().getStringExtra("subject");
        mSearch = getIntent().getStringExtra("search");
        mFilter = (Filter) getIntent().getSerializableExtra("filter");

        setToolbarTitle();
    }

    private void setToolbarTitle() {
        mToolbarTitle.setText(mSubject);

        String name;
        if (mSido.length() == 4) {
            name = "" + mSido.charAt(0) + mSido.charAt(2);
        } else {
            name = mSido.substring(0, 2);
        }
        if (mSido != null && mSigungu != null) {
            mRegionButton.setText(" " + name + " - " + mSigungu + " ");
        }
    }

    private void getDataFromServer() {
        // TODO: 2021-07-23 connect retrofit

        RESTApi mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        mRESTApi.getList().enqueue(new Callback<List<PermittedHouse>>() {
            @Override
            public void onResponse(Call<List<PermittedHouse>> call, Response<List<PermittedHouse>> response) {
                List<PermittedHouse> Result = (List<PermittedHouse>) response.body();
                PermittedList = (ArrayList) Result;
                mItemCount.setText(PermittedList.size() + "채의 집을 구경하세요");
                connectToAdapter();
//                Log.d("PermittedList", String.valueOf(PermittedList.get(0)));
//                Log.d("PermittedList", PermittedList.get(0).getResidence_name());
//                Log.d("PermittedList", "" + PermittedList.get(0).getTitleImg());
            }

            @Override
            public void onFailure(Call<List<PermittedHouse>> call, Throwable throwable) {
                Log.d("ListActivity 통신 실패", "");
            }
        });


        // todo : 1. 세진님이 upload activity, filter 완성하기
        // todo : 2. 진아님이 unchecked house 검증하기
        // todo : 3. 정후님이 houseinfo activity 연결하기
        // todo : 4. 진아님이 상세매물에서 채팅 연결하기
        // todo : 5. 진아님이 채팅에서 가계약 버튼 만들기
        // todo : 6. 세진님이 회사계좌로 가계약금 수신 및 송신, 서버에 거래 상황 갱신
        // todo : 7. 정후님이 거래중인 내역 List view, detail layout 완성, data binding
        // todo : 8. 세진님이 공인중개사 인증기능, 공인중개사 매물 list, 중개하기, 중개완료 구현

        // todo : final. Design


        // TODO: 2021-07-23 connect adapter on another thread

    }

    private void connectToAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        todo : 필터링 구현하고 주석 풀기, 어뎁터에 PermittedList_filterd 로 연결 교체
//                        filtering();
                        if(PermittedList.isEmpty() == false || PermittedList.size() != 0){
//                            Collections.sort(arr,new Filtering_for_ganada());
                            mItemCount.setText(PermittedList.size()+ "채의 아파트를 나열했어요");
                            adapter = new ListRecyclerAdapter(getApplicationContext(), PermittedList);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else if(PermittedList.size() == 0){
                            mItemCount.setText("검색 결과가 없어요.");
                        }
//                        base_progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    private void filtering() {
        if (PermittedList.isEmpty() == false || PermittedList.size() != 0) {
            for (int i = 0 ; i < PermittedList.size() ; i++) {
                // 시도, 시군구 일치 여부
                if (PermittedList.get(i).getSido().equals(mSido) && PermittedList.get(i).getSigungoo().equals(mSigungu)) {
                    // 검색어 있는 경우에 검색어 포함 여부
                    if (!mSearch.equals("") && PermittedList.get(i).getResidence_name().contains(mSearch)) {
                        // 필터 없는 경우 초기상태
                        if (mFilter == null) {
                            PermittedList_filterd.add(PermittedList.get(i));
                        } else {
                            if (mFilter.getType().equals("전체") || mFilter.getType().equals(PermittedList.get(i).getType())) {
                                // 준공일부터 작업 시작하면 될 듯
                            }
                        }
                    }
                }
            }
        }
    }

    public void calDateBetweenAandB(String date1, String date2)
    {
//        String date1 = "2016-09-21";
//        String date2 = "2016-09-10";

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(date1);
            Date SecondDate = format.parse(date2);

            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long calDateDays = calDate / ( 24*60*60*1000);

            calDateDays = Math.abs(calDateDays);

            System.out.println("두 날짜의 날짜 차이: "+calDateDays);
        }
        catch(ParseException e)
        {
            // 예외 처리
            String createDate = "2021-07-29 00:00:00";
            String test = createDate.substring(0,3) + createDate.substring(5,6) + createDate.substring(8,9);

            // 되는 거 확인 완료
            Date from = new Date();
            SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
            String to = fm.format(from);
            Log.d("to test",to);
        }
    }
}
