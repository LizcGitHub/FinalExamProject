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
import com.study.zouchao.finalexamproject_two.thirdpage.ThirdPageFragment;
import com.study.zouchao.finalexamproject_two.travellist.TravelFragment;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private int mCurFragmentIndex = 0;

    private static final int FRAGMENT_ONE_INDEX = 0;
    private static final int FRAGMENT_TWO_INDEX = 1;
    private static final int FRAGMENT_THIRD_INDEX = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initData();
        mToolbar.setTitle("明理精工 与时偕行");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mTb = (TabLayout) getCurrentFragmentRootView().findViewById(R.id.id_tl_main_table_layout);
        mTb.setupWithViewPager(mVpMain);//将TabLayout和ViewPager关联起来。
        mVpMain.setOffscreenPageLimit(5);
        mVpMain.setAdapter(new HomepagerAdapter(getActivity().getSupportFragmentManager(), mFragmentsList));
        initTabLayout();
    }

    private void initData() {
        mFragmentsList = new ArrayList<>();

        mFragmentsList.add(HomePageFragment.newInstance(null, null));
        mFragmentsList.add(SchoolPicsFragment.newInstance(null, null));
//        mFragmentsList.add(new Fragment());
//        mFragmentsList.add(new Fragment());
        mFragmentsList.add(ThirdPageFragment.newInstance());
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
        if (tab.getTag() == null)   return;
        String tag = tab.getTag().toString();
        switch (tag) {

            case "0":
                tab.setIcon(R.drawable.icon_home_blue);
                mCurFragmentIndex = FRAGMENT_ONE_INDEX;
                showToolbar(true);
                break;
            case "1":
                tab.setIcon(R.drawable.icon_img_blue);
                mCurFragmentIndex = FRAGMENT_TWO_INDEX;
                showToolbar(true);
                break;
            case "2":
                tab.setIcon(R.drawable.icon_travel_blue);
                mCurFragmentIndex = FRAGMENT_THIRD_INDEX;
                EventBusUtils.post(
                        new EventBusEvent(EventBusEvent_C.EVENT_CHECK_CURRENT_THIRD_PAGE_FRAGMENT, null, "检查当前第三个页面显示的是哪个fragment")
                );
//                showToolbar(false);
//                showToolbar(true);
                break;
            case "3":
                tab.setIcon(R.drawable.chuangxin_select);
                break;
            case "4":
                tab.setIcon(R.drawable.user_center_select);
                break;
        }
    }

    private void showToolbar(boolean isShow) {
        mToolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getTag() == null)   return;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_ON_BACK_IN_MAINACTIVITY :
                onBackInMainActivity(event);
                break;
            case EventBusEvent_C.EVENT_TOGGLE_TOOLBAR :
                toggleToolbar(event);
                break;
        }
    }

    private void toggleToolbar(EventBusEvent event) {
        Boolean isShow = (Boolean) event.getObj();
        showToolbar(isShow);
    }

    private void onBackInMainActivity(EventBusEvent event) {
        if (mCurFragmentIndex == FRAGMENT_THIRD_INDEX) {
            onBackEventToThirdFragment();
        } else {
            getActivity().finish();
        }
    }

    private void onBackEventToThirdFragment() {
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_ON_HANDLE_BACK_EVENT, "按下back"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
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
