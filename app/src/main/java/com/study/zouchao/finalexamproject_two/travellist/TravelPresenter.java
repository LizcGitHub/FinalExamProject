package com.study.zouchao.finalexamproject_two.travellist;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelCacheModel;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelPresenter implements TravelContract.ITravelPresenter {
    private Context mContext;
    private TravelContract.ITravelView mView;
    private TravelCacheModel mCacheModel;
    private TravelModel mConnModel;
    private TravelRecyclerViewAdapter mAdapter;
    private List<TravelItem> mData;

    private int temp;

    private int mDistrictId = 21;
    private int mOrderByField = 2;
    private int mPageSize = 5;
    private int mPageIndex = 1;

    public TravelPresenter(Context context, TravelContract.ITravelView view) {
        mContext = context;
        mView = view;
        mConnModel = new TravelModel();
        mCacheModel = new TravelCacheModel();
        init();
    }
    private void init() {
        mData = new ArrayList<>();
        mAdapter = new TravelRecyclerViewAdapter(mContext, mData);
        mView.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        loadDataFromCache();
        onRefreshingData();
    }

    private void loadDataFromCache() {
        List<TravelItem> data = mCacheModel.listSchoolPics(mContext);
        if (data==null)  return;
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshingData() {
        mView.showLoading(true);
        conn();
    }

    private void conn() {
        mConnModel.listTravelList(getRequestParams())
                .subscribe(new Action1<TravelListResult>() {
                    @Override
                    public void call(TravelListResult travelListResult) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void saveNewData2Cache(List<TravelItem> newData) {
        mCacheModel.save2SharePre(mContext, newData);
    }

    private void successGetList(TravelListResult travelListResult) {
        travelListResult.getResult();
//        saveNewData2Cache(data);
        loadDataFromCache();
    }

    private Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put("OrderByField", mOrderByField+"");
        params.put("DistrictId", mDistrictId+"");
        params.put("PageSize", mPageSize+"");
        params.put("PageIndex", mPageIndex+"");
        return params;
    }

}
