package com.study.zouchao.finalexamproject_two.travellist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.ui.OnSlideSeeMoreLisenter;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelFragment extends MyBaseFragment
            implements TravelContract.ITravelView, SwipeRefreshLayout.OnRefreshListener,  OnSlideSeeMoreLisenter.IOnSlideToBottomListener {
    @BindView(R.id.id_refreshing_travel)
    SwipeRefreshLayout mRefreshing;

    @BindView(R.id.id_rv_travel)
    RecyclerView mRvTravel;

    private View mSeeMoreloading;

    private TravelContract.ITravelPresenter mPresenter;


    private static final String KEY_PARAM1 = "KEY_PARAM1";

    public TravelFragment() {}

    public static TravelFragment newInstance() {
        TravelFragment fragment = new TravelFragment();
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
        mPresenter = new TravelPresenter(getActivity(), this);
        Log.d("replace>>>", "list");

        init();
    }

    private void init() {
        mRvTravel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mPresenter.initRecyclerView(mRvTravel);
        mRefreshing.setOnRefreshListener(this);
        mRvTravel.addOnScrollListener(new OnSlideSeeMoreLisenter(this));
    }

    @Override
    public void showSnackbar(String title, int showLength) {
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_SHOW_SNACK_BAR_IN_MAINFACTIVITY, null, title));
    }

    @Override
    public void onSeeMore() {
        mPresenter.loadSeeMore();
    }

    @Override
    public void showRefreshingLoading(boolean isShow) {
        if (mRefreshing != null)    mRefreshing.setRefreshing(isShow);
    }

    @Override
    public void showSeeMoreLoading(boolean isShow) {
        if (mSeeMoreloading != null) mSeeMoreloading.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setSeeMoreLoading(View footerLoadingView) {
        mSeeMoreloading = footerLoadingView;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefreshingData();
    }

    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_loading_fragment_travel);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_travel;
    }
}
