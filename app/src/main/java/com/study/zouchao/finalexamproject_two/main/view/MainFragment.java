package com.study.zouchao.finalexamproject_two.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.homepage.view.HomePageFragment;
import com.study.zouchao.finalexamproject_two.main.adapter.HomepagerAdapter;
import com.study.zouchao.finalexamproject_two.schoolpictures.view.SchoolPicsFragment;
import com.study.zouchao.finalexamproject_two.travellist.TravelFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/5/14.
 */

public class MainFragment extends MyBaseFragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {
    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.id_vp_main_view_pager)
    ViewPager mVpMain;

    @BindView(R.id.id_tl_main_table_layout)
    TabLayout mTb;

    private List<Fragment> mFragmentsList;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initData();
        mToolbar.setTitle("明理精工 与时偕行");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mTb = (TabLayout) getCurrentFragmentRootView().findViewById(R.id.id_tl_main_table_layout);
        mTb.setupWithViewPager(mVpMain);//将TabLayout和ViewPager关联起来。
        mVpMain.setOffscreenPageLimit(5);
        mVpMain.setAdapter(new HomepagerAdapter(getActivity().getSupportFragmentManager(), mFragmentsList));
        initTabLayout();
    }

    private void initData() {
        mFragmentsList = new ArrayList<>();
//        mFragmentsList.add(HomePageFragment.newInstance(null, null));
//        mFragmentsList.add(SchoolPicsFragment.newInstance(null, null));
        mFragmentsList.add(new Fragment());
        mFragmentsList.add(new Fragment());
        mFragmentsList.add(new TravelFragment());
//        mFragmentsList.add(NewsFragment.newInstance(null, null));
//        mFragmentsList.add(NewsFragment.newInstance(null, null));
    }

    private void initTabLayout() {
        mTb.setTabMode(TabLayout.MODE_FIXED);
        mTb.getTabAt(0).setIcon(R.drawable.icon_home_blue);
        mTb.getTabAt(0).setTag(0);
        mTb.getTabAt(1).setIcon(R.drawable.icon_img);
        mTb.getTabAt(1).setTag(1);
        mTb.getTabAt(2).setIcon(R.drawable.icon_travel);
        mTb.getTabAt(2).setTag(2);
//        mTb.getTabAt(3).setIcon(R.drawable.chuangxin);
//        mTb.getTabAt(3).setTag(3);
        mTb.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String tag = tab.getTag().toString();
        switch (tag) {

            case "0":
                tab.setIcon(R.drawable.icon_home_blue);
                break;
            case "1":
                tab.setIcon(R.drawable.icon_img_blue);
                break;
            case "2":
                tab.setIcon(R.drawable.icon_travel_blue);
                break;
            case "3":
                tab.setIcon(R.drawable.chuangxin_select);
                break;
            case "4":
                tab.setIcon(R.drawable.user_center_select);
                break;
        }


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        String tag = tab.getTag().toString();
        switch (tag) {
            case "0":
                tab.setIcon(R.drawable.icon_home);
                break;
            case "1":
                tab.setIcon(R.drawable.icon_img);
                break;
            case "2":
                tab.setIcon(R.drawable.icon_travel);
                break;

            case "3":
                tab.setIcon(R.drawable.chuangxin);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_loading_fragment_home);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_home;
    }
}
