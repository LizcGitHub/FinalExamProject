package com.study.zouchao.finalexamproject_two.homepage.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.homepage.adapter.MyExpandableListViewAdapter;
import com.study.zouchao.finalexamproject_two.homepage.adapter.NetworkImageHolderView;
import com.study.zouchao.finalexamproject_two.homepage.contract.IHomePageContract;
import com.study.zouchao.finalexamproject_two.homepage.model.entity.DataBean;
import com.study.zouchao.finalexamproject_two.homepage.model.impl.AdCacheModel;
import com.study.zouchao.finalexamproject_two.homepage.model.impl.AdConnModel;
import com.study.zouchao.finalexamproject_two.homepage.model.impl.CourseDataCacheModel;
import com.study.zouchao.finalexamproject_two.homepage.model.impl.CourseDataConnModel;
import com.study.zouchao.finalexamproject_two.homepage.model.result.BannerItem;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/9.
 */

public class HomePagePresenter implements IHomePageContract.IHomePagePresenter {
    private static final String TAG = "DataP";
    private Context mContext;
    //V
    private IHomePageContract.IHomePageView mView;
    private List<BannerItem> mBannerItems;
    private ConvenientBanner mConvenientBanner;
    //ExpanableListView的adapter
    private MyExpandableListViewAdapter mExpanableLvAdapter;
    //ExpanableListView的map
    private LinkedHashMap<String, List<FileInfo>> mParentChildMap;
    private AdConnModel mAdConnModel;
    private AdCacheModel mAdCacheModel;
    private CourseDataConnModel mCourseConnModel;
    private CourseDataCacheModel mCourseCacheModel;
    private boolean mIsRefreshingSchoolPics = false, mIsRefreshingCourseData = false;

    public HomePagePresenter(Context context, IHomePageContract.IHomePageView view, MyExpandableListViewAdapter.IOnChildItemClickListener listener) {
        mContext = context;
        mView = view;
        mListener = listener;
        init();
    }
    private void init() {
        mBannerItems = new ArrayList<>();
        mConvenientBanner = mView.getConvenientBanner();

        mParentChildMap = new LinkedHashMap<>();

        mAdConnModel = new AdConnModel();
        mAdCacheModel = new AdCacheModel();
        mCourseConnModel = new CourseDataConnModel();
        mCourseCacheModel = new CourseDataCacheModel();
        initView();
        loadData();
    }
    private void initView() {
        //广告栏
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mBannerItems).setPageIndicator(new int[]{R.drawable.circle_unfocused,
                        R.drawable.circle_focused});
        //expanablelistview
        mExpanableLvAdapter = new MyExpandableListViewAdapter(mContext, mParentChildMap);
        mExpanableLvAdapter.setOnChildItemClickListener(mListener);
        mView.setExpanableListViewAdapter(mExpanableLvAdapter);
    }


    @Override
    public void loadData() {
        loadAllDataFromCache();
        onRefreshingData();
    }

    private MyExpandableListViewAdapter.IOnChildItemClickListener mListener;

    @Override
    public void setOnChildListener(MyExpandableListViewAdapter.IOnChildItemClickListener listener) {
        mListener = listener;
    }

    private void loadAllDataFromCache() {
        loadAdDataFromCache();
        loadCourseDataFromCache();
    }

    @Override
    public void onRefreshingData() {
        toggleShoolPicsRefreshing(true);
        toggleCourseDataRefreshing(true);
        mView.showRefreshingAnimation(true);
        connAdPic();
        connData();
    }

    private void attemptHideRefreshingAnimation() {
        Log.i("WoCaoWOLELEGE......re.."+mIsRefreshingSchoolPics, "co.."+mIsRefreshingCourseData);
        if (!mIsRefreshingSchoolPics && !mIsRefreshingCourseData)   mView.showRefreshingAnimation(false);
    }


    private void toggleShoolPicsRefreshing(boolean isShow) {
        mIsRefreshingSchoolPics = isShow;
        attemptHideRefreshingAnimation();
    }

    private void toggleCourseDataRefreshing(boolean isShow) {
        mIsRefreshingCourseData = isShow;
        attemptHideRefreshingAnimation();
    }

    @Override
    public void connAdPic() {
        mAdConnModel.connAd(new AdConnModel.IGetAdListener() {
            @Override
            public void onSuccess(List<String> slideImgs) {
                successGetAdPics(slideImgs);
                toggleShoolPicsRefreshing(false);
                Log.i("Loading..false", "connAdPic..onSuccess");
            }
            @Override
            public void onFailure(Throwable throwable, String msg) {
                toggleShoolPicsRefreshing(false);
//                ToastUtils.showShort(mContext.getApplicationContext(), msg);
                Log.i("Loading..false", "connAdPic..onfailure");
            }
        });
    }

    private void successGetAdPics(List<String> newData) {
        saveNewData2Cache(newData);
        loadAdDataFromCache();
    }

    private void saveNewData2Cache(List<String> newData) {
        mAdCacheModel.save2SharePre(mContext, newData);
//        ToastUtils.showShort(mContext, "轮播图更新成功");
    }

    private void loadAdDataFromCache() {
        List<String> data = mAdCacheModel.listAdPics(mContext);
        if (data == null)  return;
        showAd(data);
    }

    private void showAd(List<String> slideImgs) {
        List<BannerItem> items = new ArrayList<>();
        //生成所需的数据
        for (int i = 0; i < slideImgs.size(); i ++) {
            items.add(new BannerItem("第" + i + "张", slideImgs.get(i)));
        }
        //广告栏
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, items).setPageIndicator(new int[]{R.drawable.circle_unfocused, R.drawable.circle_focused});
    }

    /**
     * CourseData
     */
    @Override
    public void connData() {
        mCourseConnModel.connCourseData(new CourseDataConnModel.IGetCourseDataListener() {
            @Override
            public void onSuccess(LinkedHashMap<String, List<DataBean>> data) {
                sucessGetCourseData(data);
                toggleCourseDataRefreshing(false);
//                ToastUtils.showShort(mContext, "下载列表更新成功");
            }
            @Override
            public void onFailure(Throwable throwable, String msg) {
                toggleCourseDataRefreshing(false);
//                ToastUtils.showShort(mContext.getApplicationContext(), msg);
            }
        });
    }

    private void sucessGetCourseData(LinkedHashMap<String, List<DataBean>> data) {
        LinkedHashMap<String, List<FileInfo>> newData = parse2ExpandableListViewData(data);
        saveCourseData2Cache(newData);
        loadCourseDataFromCache();
    }

    private LinkedHashMap<String, List<FileInfo>> parse2ExpandableListViewData(LinkedHashMap<String, List<DataBean>> data) {
        LinkedHashMap<String, List<FileInfo>> newData = new LinkedHashMap<>();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            List<DataBean> dataBeans = data.get(key);
            List<FileInfo> infoList = new ArrayList<>();
            for (DataBean dataBean : dataBeans) {
                Log.i("怎么没标题conn", dataBean.toString());
                infoList.add(new FileInfo(0, dataBean.dataDownloadUrl, dataBean.title, 0, 0, dataBean.totalSize));
            }
            newData.put(key, infoList);
        }
        return newData;
    }

    /**
     * Cache
     */
    private void saveCourseData2Cache(LinkedHashMap<String, List<FileInfo>> newData) {
        mCourseCacheModel.save2SharePre(mContext, newData);
    }

    private void loadCourseDataFromCache() {
        LinkedHashMap<String, List<FileInfo>> cacheData = mCourseCacheModel.listCoureseDataByMap(mContext);
        if (cacheData == null)  return;
        Log.i("怎么没标题", cacheData.toString());
        mParentChildMap.clear();
        mParentChildMap.putAll(cacheData);
        mExpanableLvAdapter.notifyDataSetChanged();
    }
}
