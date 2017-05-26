package com.study.zouchao.finalexamproject_two.schoolpictures.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseFragment;
import com.study.zouchao.finalexamproject_two.schoolpictures.adapter.MyRecyclerViewAdapter;
import com.study.zouchao.finalexamproject_two.schoolpictures.contact.SchoolPicsContract;
import com.study.zouchao.finalexamproject_two.schoolpictures.presenter.SchoolPicsPresenter;

import butterknife.BindView;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by Administrator on 2017/5/17.
 */

public class SchoolPicsFragment extends MyBaseFragment implements SchoolPicsContract.ISchoolPicsView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.id_refreshing)
    SwipeRefreshLayout mRefreshing;
    @BindView(R.id.id_rv)
    RecyclerView mRv;
    public SchoolPicsFragment() {}
    private static final String KEY_PARAM1 = "KEY_PARAM1",
            KEY_PARAM2 = "KEY_PARAM2";
    private SchoolPicsContract.ISchoolPicsPresenter mPresenter;
    public static SchoolPicsFragment newInstance(String param1, String param2) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PARAM1, param1);
        bundle.putString(KEY_PARAM2, param2);
        SchoolPicsFragment instance = new SchoolPicsFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mRv.setAdapter(new MyRecyclerViewAdapter(getActivity(), null));
        mPresenter = new SchoolPicsPresenter(getActivity(), this);
        mRefreshing.setOnRefreshListener(this);
    }


    @Override
    public void setAdapter(MyRecyclerViewAdapter adapter) {
        mRv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View imageView, String url) {

    }

    @Override
    public void showRefreshingAnimation(boolean isShow) {
        if (mRefreshing != null)    mRefreshing.setRefreshing(isShow);
    }

    @Override
    protected View getLoadingTargetView() {
        return getCurrentFragmentRootView().findViewById(R.id.id_loading_schoolpics);
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_schoolpics;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefreshingData();
    }
}
