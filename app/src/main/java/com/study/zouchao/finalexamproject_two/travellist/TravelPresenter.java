package com.study.zouchao.finalexamproject_two.travellist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.tuple.FiveTuple;
import com.study.zouchao.finalexamproject_two.base_zou.tuple.FourTuple;
import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelCacheModel;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelModel;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
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

    private Subscription mSupConn;

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
    }

    private void toggleLoading(String userAction, boolean isShow) {
        if (userAction.equals(ACTION_REFRESHING))   mView.showRefreshingLoading(isShow);
        if(userAction.equals(ACTION_SEE_MORE))      mView.showSeeMoreLoading(isShow);
    }

    @Override
    public void onRefreshingData() {
        conn(ACTION_REFRESHING);
    }

    @Override
    public void loadSeeMore() {
        Log.d("加载更多", "滚动事件存在");
        if (mIsLoadingData)      return;
        //无数据的情况只能下拉刷新不允许上滑更多   1是footView
        if (mAdapter.getItemCount()<=1)   return;
        if ((mPageIndex)*mPageSize >= mTotalSize) {
            mView.showSnackbar("无更多数据", Snackbar.LENGTH_INDEFINITE);
            return;
        }
        conn(ACTION_SEE_MORE);
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
        mSupConn = mConnModel.listTravelList(requestParams)
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
                        failGetList(throwable, action);
                        toggleLoading(action, false);
                        mIsLoadingData = false;
                    }
                });
    }

    private void successGetList(TravelListResult result, String action) {
        if (action.equals(ACTION_REFRESHING)) {
            mData.clear();
            saveNewData2Cache(result.getResult());
        }
        mTotalSize = result.getTotalCount();
        List<TravelListResult.ResultBean> newData = result.getResult();
        mData.addAll(newData);
        mAdapter.notifyDataSetChanged();
    }

    private void failGetList(Throwable throwable, String action) {
        throwable.printStackTrace();
        ToastUtils.showLong(mContext, throwable.getMessage());
        if (action.equals(ACTION_REFRESHING))
            loadDataFromCache();
    }

    private void saveNewData2Cache(List<TravelListResult.ResultBean> newData) {
        mCacheModel.saveTravelData2SharePre(mContext, newData);
    }

    private void loadDataFromCache() {
        List<TravelListResult.ResultBean> cacheData =  mCacheModel.listTravelData(mContext);
        mData.clear();
        mData.addAll(cacheData);
        mAdapter.notifyDataSetChanged();
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
        FourTuple travelDetailInfos = new FourTuple<>(v, url, title, bgImg);
        EventBusUtils.post(
                new EventBusEvent(EventBusEvent_C.EVENT_GOTO_TRAVEL_DETAIL_FRAGMENT, travelDetailInfos, "")
        );
    }

    @Override
    public void onDestroyPresente() {
        if (mSupConn!=null && !mSupConn.isUnsubscribed())    mSupConn.unsubscribe();
    }
}
