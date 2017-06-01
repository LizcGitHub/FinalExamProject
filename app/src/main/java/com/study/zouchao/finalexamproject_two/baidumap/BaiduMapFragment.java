package com.study.zouchao.finalexamproject_two.baidumap;

import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;

/**
 * Created by Administrator on 2017/5/31.
 */

public class BaiduMapFragment extends MyBaseFragment {
    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.sliding_layout);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_baidu_map;
    }
}
