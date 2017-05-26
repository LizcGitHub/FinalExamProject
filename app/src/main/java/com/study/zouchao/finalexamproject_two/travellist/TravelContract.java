package com.study.zouchao.finalexamproject_two.travellist;

import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface TravelContract {
    interface ITravelModel {

    }
    interface ITravelView {
        void setAdapter(TravelRecyclerViewAdapter adapter);
        void showLoading(boolean isShow);
    }
    interface ITravelPresenter {
        void onRefreshingData();
    }
}
