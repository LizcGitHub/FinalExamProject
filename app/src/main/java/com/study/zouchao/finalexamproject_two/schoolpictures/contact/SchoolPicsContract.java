package com.study.zouchao.finalexamproject_two.schoolpictures.contact;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.study.zouchao.finalexamproject_two.schoolpictures.adapter.MyRecyclerViewAdapter;


/**
 * Created by Administrator on 2017/5/15.
 */

public class SchoolPicsContract {
    public interface ISchoolPicsPresenter {
        void onRefreshingData();
    }
    public interface ISchoolPicsView {
        void setAdapter(MyRecyclerViewAdapter adapter);
        void onItemClick(View imageView, String url);
        void showRefreshingAnimation(boolean isShow);
    }
}
