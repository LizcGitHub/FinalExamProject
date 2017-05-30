package com.study.zouchao.finalexamproject_two.searchbusactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.searchbusactivity.adapter.BusShowAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class SearchBusActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSearch;
    private TextView mTvSearchCity;
    private EditText mEtInputBus;

    private RecyclerView mRvBusDetail;
    private BusShowAdapter adapter;

    private String busLineId;
    private BusLineSearch mBusLineSearch;
    private PoiSearch mSearch;
    private List<BusLineResult.BusStation> mBusDetails;
    private TextView mHCenterLine;
    private TextView mVCenterLine;
    private TextView mTvBeginStation;
    private TextView mTvEndStation;
    private TextView mTvDirection;
    private TextView mTvTime;
    private CardView mCvContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus);
        mBusDetails = new ArrayList<>();
        initView();
    }

    private void initView() {
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mTvSearchCity = (TextView) findViewById(R.id.tv_search_city);
        mEtInputBus = (EditText) findViewById(R.id.et_input_bus);
        mRvBusDetail = (RecyclerView) findViewById(R.id.rv_bus_detail);
        mHCenterLine = (TextView) findViewById(R.id.h_center_line);
        mVCenterLine = (TextView) findViewById(R.id.v_center_line);
        mTvBeginStation = (TextView) findViewById(R.id.tv_begin_station);
        mTvEndStation = (TextView) findViewById(R.id.tv_end_station);
        mTvDirection = (TextView) findViewById(R.id.tv_direction);
        mTvTime = (TextView) findViewById(R.id.tv_time);

        mHCenterLine.setOnClickListener(this);
        mVCenterLine.setOnClickListener(this);
        mTvBeginStation.setOnClickListener(this);
        mTvEndStation.setOnClickListener(this);
        mTvDirection.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mTvSearchCity.setOnClickListener(this);
        mEtInputBus.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);

        mBusLineSearch = BusLineSearch.newInstance();
        mSearch = PoiSearch.newInstance();

        mSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                //遍历所有POI，找到类型为公交线路的POI
                for (PoiInfo poi : result.getAllPoi()) {
                    if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                        //说明该条POI为公交信息，获取该条POI的UID
                        busLineId = poi.uid;
                        break;
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                Log.e("", "onGetBusLineResult: " + busLineResult.toString());

                //处理上行下行目的地
                String title = busLineResult.getBusLineName();
                int startMark = title.indexOf("(");
                int endMark = title.lastIndexOf(")");
                title = title.substring(startMark + 1, endMark);
                String[] upAnddownTitle = title.split("-");
                String upTitle = upAnddownTitle[0];
                String downTitle = upAnddownTitle[1];

                mTvBeginStation.setText(upTitle);
                mTvEndStation.setText(downTitle);

                //获得开始时间 结束时间
                Date beginTime = busLineResult.getStartTime();
                Date endTime = busLineResult.getEndTime();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

                mTvTime.setText(formatter.format(beginTime) + " -- " + formatter.format(endTime));
                mTvDirection.setText(busLineResult.getLineDirection());

                //显示公交站点
                mBusDetails.clear();
                mBusDetails.addAll(busLineResult.getStations());
                adapter.notifyDataSetChanged();
            }
        });
        mEtInputBus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //发生改变一次 查询一次
                mSearch.searchInCity((new PoiCitySearchOption())
                        .city("厦门")
                        .isReturnAddr(false)
                        .keyword(s.toString())
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRvBusDetail.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new BusShowAdapter(mBusDetails);
        mRvBusDetail.setAdapter(adapter);

        mCvContent = (CardView) findViewById(R.id.cv_content);
        mCvContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search: {
                submit();
            }
            break;
        }
    }

    private void submit() {
        // validate
        final String bus = mEtInputBus.getText().toString().trim();
        if (TextUtils.isEmpty(bus)) {
            Toast.makeText(this, "bus不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (busLineId != null) {
            mBusLineSearch.searchBusLine((new BusLineSearchOption()
                    .city("厦门")
                    .uid(busLineId)));
        }
        mCvContent.setVisibility(View.VISIBLE);
    }

    public static void actionSearchBusActivity(Context context) {
        Intent intent = new Intent(context, SearchBusActivity.class);
        context.startActivity(intent);
    }
}
