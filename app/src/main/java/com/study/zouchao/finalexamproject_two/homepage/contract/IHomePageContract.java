package com.study.zouchao.finalexamproject_two.homepage.contract;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.study.zouchao.finalexamproject_two.homepage.adapter.MyExpandableListViewAdapter;


/**
 * Created by Administrator on 2017/5/15.
 */

public class IHomePageContract {
    public interface IHomePagePresenter {
        void connAdPic();
        void connData();
        void onRefreshingData();
        void loadData();
        void setOnChildListener(MyExpandableListViewAdapter.IOnChildItemClickListener listener);
    }
    public interface IHomePageView {
        //得到轮播图
        ConvenientBanner getConvenientBanner();
        //设置
        void setExpanableListViewAdapter(MyExpandableListViewAdapter adapter);
        //
        void showRefreshingAnimation(boolean isShow);
    }
}
