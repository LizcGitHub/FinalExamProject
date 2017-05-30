package com.study.zouchao.finalexamproject_two.homepage.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.homepage.adapter.MyExpandableListViewAdapter;
import com.study.zouchao.finalexamproject_two.homepage.contract.IHomePageContract;
import com.study.zouchao.finalexamproject_two.homepage.presenter.HomePagePresenter;
import com.study.zouchao.finalexamproject_two.service.DownloadService;
import com.study.zouchao.finalexamproject_two.util.DialogUtil;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/5/14.
 */

public class HomePageFragment extends MyBaseFragment
        implements IHomePageContract.IHomePageView, SwipeRefreshLayout.OnRefreshListener, ExpandableListView.OnChildClickListener, MyExpandableListViewAdapter.IOnChildItemClickListener {
    @BindView(R.id.id_refreshing)
    SwipeRefreshLayout mRefreshing;
    //广告栏（简介）
    @BindView(R.id.cb_ad_image)
    ConvenientBanner mConvenientBanner;
    //ExpanableList
    @BindView(R.id.id_expandable_listview_data)
    ExpandableListView mExpandableLv;
    //P
    private HomePagePresenter mPresenter;

    private AlertDialog mDialog;

    public HomePageFragment() {}
    private static final String KEY_PARAM1 = "KEY_PARAM1",
                                KEY_PARAM2 = "KEY_PARAM2";
    private String mParam1, mParam2;

    public static HomePageFragment newInstance(String param1, String param2) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PARAM1, param1);
        bundle.putString(KEY_PARAM2, param2);
        HomePageFragment instance = new HomePageFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(KEY_PARAM1);
            mParam2 = getArguments().getString(KEY_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mPresenter = new HomePagePresenter(getContext(), this, this);
        mPresenter.setOnChildListener(this);
        mExpandableLv.setDivider(null);
        //TODO:屏幕宽度 不要设死
        mExpandableLv.setIndicatorBounds(1080 - 90, 1080 - 30);
//        mExpandableLv.setOnChildClickListener(this);
        mRefreshing.setOnRefreshListener(this);
    }

    @Override
    public ConvenientBanner getConvenientBanner() {
        return mConvenientBanner;
    }

    @Override
    public void setExpanableListViewAdapter(MyExpandableListViewAdapter adapter) {
        mExpandableLv.setAdapter(adapter);
    }

    @Override
    public void showRefreshingAnimation(boolean isShow) {
        if (mRefreshing != null) mRefreshing.setRefreshing(isShow);
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefreshingData();
    }


    @Override
    public void onClick(final FileInfo fileInfo, int parentPosition, int childPosition) {
        mDialog = DialogUtil.showAlertDialog(getActivity(), "确认下载？", "文件："+fileInfo.getFileName(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownloadService.actionStartAddDownload(getActivity(), fileInfo);
            }
        }, null);
        mDialog.show();
    }


    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_loading_homepage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog!=null && mDialog.isShowing())   mDialog.dismiss();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_homepage;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

}
