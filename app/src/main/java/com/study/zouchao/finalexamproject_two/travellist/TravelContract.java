package com.study.zouchao.finalexamproject_two.travellist;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.zouchao.finalexamproject_two.travellist.adapter.TravelRecyclerViewAdapter;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface TravelContract {
    interface ITravelModel {

    }
    interface ITravelView {
        void setAdapter(TravelRecyclerViewAdapter adapter);

        void showRefreshingLoading(boolean isShow);
        void showSeeMoreLoading(boolean isShow);
        void setSeeMoreLoading(View footerLoadingView);
    }
    interface ITravelPresenter {
        void onRefreshingData();
        void loadSeeMore();
        void initRecyclerView(RecyclerView rv);
    }
}
