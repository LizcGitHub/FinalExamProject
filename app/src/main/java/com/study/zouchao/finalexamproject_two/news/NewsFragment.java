package com.study.zouchao.finalexamproject_two.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.schoolpictures.contact.SchoolPicsContract;

import butterknife.BindView;
/**
 * Created by Administrator on 2017/5/17.
 */
public class NewsFragment extends MyBaseFragment {
//    @BindView(R.id.id_mapview)
//    MapView mMapView;

    private static final String KEY_PARAM1 = "KEY_PARAM1",
            KEY_PARAM2 = "KEY_PARAM2";
    private SchoolPicsContract.ISchoolPicsPresenter mPresenter;
    public static NewsFragment newInstance(String param1, String param2) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PARAM1, param1);
        bundle.putString(KEY_PARAM2, param2);
        NewsFragment instance = new NewsFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mMapView != null)   mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        if (mMapView != null)   mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
//        if (mMapView != null)   mMapView.onPause();
    }


    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return 0;
    }


}
