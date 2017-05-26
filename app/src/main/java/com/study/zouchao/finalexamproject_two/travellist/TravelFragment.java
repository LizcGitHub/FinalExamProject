package com.study.zouchao.finalexamproject_two.travellist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelFragment extends MyBaseFragment implements TravelContract.ITravelView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.id_refreshing_travel)
    SwipeRefreshLayout mRefreshing;

    @BindView(R.id.id_rv_travel)
    RecyclerView mRvTravel;

    private TravelContract.ITravelPresenter mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new TravelPresenter(getActivity(), this);
        init();
    }

    private void init() {
        mRvTravel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mRefreshing.setOnRefreshListener(this);
    }

    @Override
    public void setAdapter(TravelRecyclerViewAdapter adapter) {
        mRvTravel.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(boolean isShow) {
        mRefreshing.setRefreshing(isShow);
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
