package com.study.zouchao.finalexamproject_two.thirdpage;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.base_zou.tuple.FiveTuple;
import com.study.zouchao.finalexamproject_two.base_zou.tuple.FourTuple;
import com.study.zouchao.finalexamproject_two.traveldetail.TravelDetailFragment;
import com.study.zouchao.finalexamproject_two.travellist.TravelFragment;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/29.
 */

public class ThirdPageFragment extends MyBaseFragment {
    private LinkedList<Fragment> mBackStack;

    public ThirdPageFragment()  {}

    public static ThirdPageFragment newInstance() {
        ThirdPageFragment fragment = new ThirdPageFragment();
        fragment.setArguments(null);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("replace>>>", "3");
        init();
    }

    private void init() {
        EventBusUtils.register(this);
        mBackStack = new LinkedList<>();
        initStack();
    }

    private void initStack() {
        TravelFragment travelFragment = TravelFragment.newInstance();
//        replaceFragment(travelFragment);
        addFragment(travelFragment);
        mBackStack.add(travelFragment);
        showFragment(travelFragment);
        toggleMainFragmentToolbar();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.id_loading_third_fragment, fragment)
                .commit();
        //TODO:commit是一个异步操作、考虑是否需要个方法判断是否真的commit成功
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.id_loading_third_fragment, fragment)
                .commit();
    }

    private void hideFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
//                .add(R.id.id_loading_third_fragment, fragment)
                .hide(fragment)
                .commit();
    }


    public void removeFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
//                .add(R.id.id_loading_third_fragment, fragment)
                .remove(fragment)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
//                .add(R.id.id_loading_third_fragment, fragment)
//                .remove(fragment)
                .show(fragment)
                .commit();
    }

    private void toggleMainFragmentToolbar() {
        if (mBackStack.size() <= 0)  return;
        Fragment curFragment = mBackStack.getLast();
        Boolean isShow = curFragment.getClass()==TravelFragment.class;
        EventBusEvent event = new EventBusEvent(EventBusEvent_C.EVENT_TOGGLE_TOOLBAR, isShow, "控制toolbar的显示与隐藏");
        EventBusUtils.post(event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_GOTO_TRAVEL_DETAIL_FRAGMENT :
                gotoTravelDetailFragment(event);
                break;
            case EventBusEvent_C.EVENT_ON_HANDLE_BACK_EVENT :
                onBack(event);
                break;
            case EventBusEvent_C.EVENT_CHECK_CURRENT_THIRD_PAGE_FRAGMENT :
                onCheckCurrentFragment(event);
                break;
        }
    }

    private void gotoTravelDetailFragment(EventBusEvent event) {
        FourTuple<View, String, String, String> travelDetailInfos
                = (FourTuple<View, String, String, String>) event.getObj();
        Fragment travelDetailFragment = TravelDetailFragment.newInstance(travelDetailInfos.b, travelDetailInfos.c, travelDetailInfos.d);
//        replaceFragment(travelDetailFragment);
        addFragment(travelDetailFragment);
        if (mBackStack.size() > 0)  hideFragment(mBackStack.getLast());
        showFragment(travelDetailFragment);
        mBackStack.add(travelDetailFragment);
        toggleMainFragmentToolbar();
    }

    private void onBack(EventBusEvent event) {
        if (mBackStack.size() > 1) {
            Fragment fragment = mBackStack.getLast();
            removeFragment(fragment);
            mBackStack.removeLast();
            showFragment(mBackStack.getLast());
            toggleMainFragmentToolbar();
        } else {
            mBackStack.removeLast();
            getActivity().finish();
        }
    }


    private void onCheckCurrentFragment(EventBusEvent event) {
        toggleMainFragmentToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
        Log.d("返回栈生命周期", "333:onDestroy");
    }

    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_loading_third_fragment);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_third;
    }
}
