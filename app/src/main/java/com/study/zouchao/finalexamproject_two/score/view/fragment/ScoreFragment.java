package com.study.zouchao.finalexamproject_two.score.view.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.score.contract.IScoreContract;
import com.study.zouchao.finalexamproject_two.score.presenter.ScorePresenter;
import com.study.zouchao.finalexamproject_two.util.DialogUtil;
import com.study.zouchao.finalexamproject_two.util.ui.MyPickViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/24.
 */

public class ScoreFragment extends Fragment implements IScoreContract.IScoreView {
    /**
     * 本Fragment的内容view
     */
    private View mContentView;

    /**
     * 成绩列表
     */
    @BindView(R.id.id_recycleview)
    RecyclerView mRecyclerView;

    /**
     * 悬浮按钮 用于选择学期
     */
    @BindView(R.id.id_fab)
    FloatingActionButton mFab;

    /**
     * Loading动画
     */
    ProgressDialog mLoading;

    private IScoreContract.IScorePresenter mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_score, container, false);
        ButterKnife.bind(this, mContentView);
        mPresenter = new ScorePresenter(getContext(), this);
        initView();
        return mContentView;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        /*
            recycleView一定要设置布局管理器
                可选: 线性, 网格, 瀑布流
         */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 设置RecycleView的adapter
     * @param adapter
     */
    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 提示框
     * @param promoteTv
     */
    @Override
    public void showDialog(String promoteTv) {
        //弹出一个提示框
        DialogUtil.showSingleAlertDialog(getContext(), "提示：", promoteTv, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishCurrentActivity();
            }
        });
    }

    /**
     * 显示Loading动画
     * @param isShow
     */
    @Override
    public void showLoading(boolean isShow) {
        if (isShow)
            mLoading = ProgressDialog.show(getContext(), "", "正在加载...");
        else mLoading.dismiss();
    }

    /**
     * 关闭当前Activity
     */
    private void finishCurrentActivity() {
        getActivity().finish();
    }

    /**
     * 点击监听器
     * @param view
     */
    @OnClick({R.id.id_fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_fab :
                showPick();
                break;
        }
    }

    /**
     * 弹出学期选择
     */
    private void showPick() {
        Intent intent = new Intent(getActivity(), MyPickViewActivity.class);
        startActivity(intent);
    }

    /**
     * 生命周期
     */
    @Override
    public void onDestroy() {
        //销毁
        mPresenter.onDestroyPresenter();
        super.onDestroy();
    }
}
