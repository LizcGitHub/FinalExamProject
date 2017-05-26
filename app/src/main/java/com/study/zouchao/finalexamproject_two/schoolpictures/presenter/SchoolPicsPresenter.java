package com.study.zouchao.finalexamproject_two.schoolpictures.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.study.zouchao.finalexamproject_two.schoolpictures.adapter.MyRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.schoolpictures.contact.SchoolPicsContract;
import com.study.zouchao.finalexamproject_two.schoolpictures.model.SchoolPicsCacheModel;
import com.study.zouchao.finalexamproject_two.schoolpictures.model.SchoolPicsModel;
import com.study.zouchao.finalexamproject_two.util.ui.photoview.DragPhotoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/5/17.
 */

public class SchoolPicsPresenter implements SchoolPicsContract.ISchoolPicsPresenter, MyRecyclerViewAdapter.IRvItemClickListener {
    private static final String TAG = "SchoolPicsP";
    private List<String> mData;
    private Context mContext;
    private SchoolPicsContract.ISchoolPicsView mView;
    private MyRecyclerViewAdapter mAdapter;
    private SchoolPicsCacheModel mCacheModel;
    private SchoolPicsModel mConnModel;
    public SchoolPicsPresenter(Context context, SchoolPicsContract.ISchoolPicsView view) {
        mContext = context;
        mView = view;
        mCacheModel = new SchoolPicsCacheModel();
        mConnModel = new SchoolPicsModel();
        init();
    }

    private void init() {
        mData = new ArrayList<>();
        mAdapter = new MyRecyclerViewAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(this);
        mView.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        loadDataFromCache();
        onRefreshingData();
    }

    @Override
    public void onRefreshingData() {
        mView.showRefreshingAnimation(true);
        connPics();
    }

    private void connPics() {
        mConnModel.connPics(new SchoolPicsModel.IGetShoolPicsListener() {
            @Override
            public void onSuccess(List<String> data) {
                successGetSchoolPics(data);
                mView.showRefreshingAnimation(false);
            }
            @Override
            public void onFailure(Throwable throwable, String msg) {
                failGetSchoolPics(throwable, msg);
                mView.showRefreshingAnimation(false);
            }
        });
    }

    private void saveNewData2Cache(List<String> newData) {
        mCacheModel.save2SharePre(mContext, newData);
    }

    private void loadDataFromCache() {
        List<String> data = mCacheModel.listSchoolPics(mContext);
        if (data==null)  return;
        mData.clear();
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    private void successGetSchoolPics(List<String> data) {
        saveNewData2Cache(data);
        loadDataFromCache();
    }

    private void failGetSchoolPics(Throwable throwable, String msg) {
//        throwable.printStackTrace();
//        ToastUtils.showShort(mContext.getApplicationContext(), msg);
    }

    @Override
    public void onItemClick(View imageView, String url) {
        DragPhotoActivity.actionStartDragPhotoActivity(mContext, (ImageView) imageView, url);
    }
}
