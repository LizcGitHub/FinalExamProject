package com.study.zouchao.finalexamproject_two.util.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import com.study.zouchao.finalexamproject_three.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class MyPickViewActivity extends Activity {
    @BindView(R.id.id_tv_sure)
    TextView mTvSure;
    /**
     * 学年选择器
     */
    @BindView(R.id.id_pv_year)
    WheelView mPickYear;
    /**
     * 学期选择器
     */
    @BindView(R.id.id_pv_term)
    WheelView mPickTerm;
    /**
     * 学年数据
     */
    private List<String> mYearData;
    /**
     * 学期数据
     */
    private List<String> mTermData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_pick_view);
        //作为对话框点击外部不消失
        setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mYearData = new ArrayList<>();
        mTermData = new ArrayList<>();
    }

    private void initView() {
        mPickYear.setWheelAdapter(new ArrayWheelAdapter(this));
        mPickYear.setSkin(WheelView.Skin.Holo);
        mPickYear.setWheelData(initYearData());
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        mPickYear.setStyle(style);

        mPickTerm.setWheelAdapter(new ArrayWheelAdapter(this));
        mPickTerm.setSkin(WheelView.Skin.Holo);
        mPickTerm.setWheelData(initTermData());
        mPickTerm.setStyle(style);
    }

    private List<String> initYearData() {
        List<String> list = new ArrayList<>();
        for (int year = 2017; year > 2001; year --) {
            //eg: 2015-2016
            list.add((year-1)+"-"+year);
        }
        return list;
    }

    private List<String> initTermData() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        return list;
    }

    /**
     * 生命周期
     */
    @Override
    protected void onPause() {
        //去掉 finish 动画
        overridePendingTransition(0,0);
        super.onPause();
    }

    @OnClick({R.id.id_tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_sure :
                setYearAndTerm();
                finish();
                break;
        }
    }

    private void setYearAndTerm() {
        String year = (String) mPickYear.getSelectionItem();
        String term = (String) mPickTerm.getSelectionItem();
        //发送广播
        EventBus.getDefault().post(year+"~"+term);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
