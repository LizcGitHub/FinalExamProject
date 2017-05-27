package com.study.zouchao.finalexamproject_two.travellist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.traveldetail.TravelDetailActivity;
import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelCacheModel;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelModel;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelPresenter implements TravelContract.ITravelPresenter, TravelRecyclerViewAdapter.IRvItemClickListener {
    private static final String TAG = "TravelP";
    private Context mContext;
    private TravelContract.ITravelView mView;
    private TravelCacheModel mCacheModel;
    private TravelModel mConnModel;
    private TravelRecyclerViewAdapter mAdapter;
//    private List<TravelItem> mData;
    private List<TravelListResult.ResultBean> mData;


    //用户操作：下拉刷新
    private static final String ACTION_REFRESHING = "ACTION_REFRESHING";
    //用户操作：上滑更多
    private static final String ACTION_SEE_MORE = "ACTION_SEE_MORE";

    //正在加载数据
    private boolean mIsLoadingData = false;

    private int mDistrictId = 21;
    private int mOrderByField = 2;
    private int mPageSize = 10;
    private int mPageIndex = 1;
    private int mPhotoHeight = 320;
    private int mPhotoWidth = 640;
    private int mTotalSize = 0;

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
//        mView.setAdapter(mAdapter);
    }

    private void toggleLoading(String userAction, boolean isShow) {
        if (userAction.equals(ACTION_REFRESHING))   mView.showRefreshingLoading(isShow);
        if(userAction.equals(ACTION_SEE_MORE))      mView.showSeeMoreLoading(isShow);
    }

    private void loadData() {
//        loadDataFromCache();
        onRefreshingData();
    }

    private void loadDataFromCache() {
//        List<TravelItem> data = mCacheModel.listSchoolPics(mContext);
//        if (data==null)  return;
//        mData.clear();
//        mData.addAll(data);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshingData() {
        Log.d("正在加载》？》", mIsLoadingData+"");
//        if (mIsLoadingData) {
//            ToastUtils.showShort(mContext.getApplicationContext(), "已经在加载数据、请稍后再试");
//            toggleLoading(ACTION_REFRESHING, false);
//            return;
//        }
        conn(ACTION_REFRESHING);
    }

    @Override
    public void loadSeeMore() {
        Log.d("加载更多", "滚动事件存在");
        if (mIsLoadingData)      return;
        //无数据的情况只能下拉刷新不允许上滑更多   1是footView
        if (mAdapter.getItemCount()<=1)   return;
        if ((mPageIndex)*mPageSize >= mTotalSize) {
            ToastUtils.showShort(mContext.getApplicationContext(), "无更多数据");
            return;
        }
//        Log.d("当前pageNo"+mPageNo, "totalsize"+mTotalSize+"。。。我们总数据"+(mAdapter.getItemCount()-1));
        conn(ACTION_SEE_MORE);
        Log.d("加载更多", "已经加载");
    }

    private void conn(final String action) {
        if (!isValidUserAction(action)) {
            Toast.makeText(mContext, "用户操作有误", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> requestParams = getRequestParams(action);
        if (requestParams==null || requestParams.isEmpty()) return;
        toggleLoading(action, true);
        mIsLoadingData = true;
        mConnModel.listTravelList(requestParams)
                .subscribe(new Action1<TravelListResult>() {
                    @Override
                    public void call(TravelListResult travelListResult) {
                        successGetList(travelListResult, action);
                        toggleLoading(action, false);
                        mIsLoadingData = false;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        ToastUtils.showLong(mContext, throwable.getMessage());
                        toggleLoading(action, false);
                        mIsLoadingData = false;
                    }
                });
    }

    private void saveNewData2Cache(List<TravelItem> newData) {
        mCacheModel.save2SharePre(mContext, newData);
    }

    private void successGetList(TravelListResult result, String action) {
        if (action.equals(ACTION_REFRESHING))   mData.clear();
        mTotalSize = result.getTotalCount();
        Log.d("加载更多", "zong:"+mTotalSize);
        List<TravelListResult.ResultBean> newData = result.getResult();
//        ToastUtils.showLong(mContext, newData.toString());
        mData.addAll(newData);
        mAdapter.notifyDataSetChanged();
//        saveNewData2Cache(data);
//        loadDataFromCache();
    }


    private boolean isValidUserAction(String action) {
        return (action!=null) && (action.equals(ACTION_REFRESHING) || action.equals(ACTION_SEE_MORE));
    }


    private Map<String, String> getRequestParams(String action) {
        if(action.equals(ACTION_REFRESHING)) mPageIndex = 1;
        if (action.equals(ACTION_SEE_MORE))  mPageIndex += 1;
        Map<String, String> params = new HashMap<>();
        params.put("OrderByField", mOrderByField+"");
        params.put("DistrictId", mDistrictId+"");
        params.put("PageSize", mPageSize+"");
        params.put("PageIndex", mPageIndex+"");
        params.put("PhotoHeight", mPhotoHeight+"");
        params.put("PhotoWidth", mPhotoWidth+"");
        Log.i(TAG+"请求travelitemlist的参数:.."+params.toString(), "请求的页码:"+mPageIndex);
        return params;
    }

    /**
     * 初始化RecyclerView的布局
     */
    @Override
    public void initRecyclerView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mData = new ArrayList<>();
        //为RecyclerView加Adapter
        mAdapter = new TravelRecyclerViewAdapter(mContext, mData);
        View footLoadingView = LayoutInflater.from(mContext).inflate(R.layout.footview_recyclerview, rv, false);
        mAdapter.setFooterView(footLoadingView);
        mAdapter.setOnItemClickListener(this);
        mView.setSeeMoreLoading(footLoadingView);
        rv.setAdapter(mAdapter);
        //添加上滑更多监听器
        //请求数据
        conn(ACTION_REFRESHING);
    }

    @Override
    public void onItemClick(View v, String title, String bgImg, String url) {
        //TODO:跳转：替换Fragment
        TravelDetailActivity.actionStartTravelDetailActivity(mContext, title, bgImg, url);
    }
}
