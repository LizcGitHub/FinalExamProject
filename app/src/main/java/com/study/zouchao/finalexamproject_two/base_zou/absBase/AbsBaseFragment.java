package com.study.zouchao.finalexamproject_two.base_zou.absBase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.zouchao.finalexamproject_two.base_zou.loading.ILoadingDelegate;
import com.study.zouchao.finalexamproject_two.base_zou.loading.VaryViewHelperController;
import com.study.zouchao.finalexamproject_two.util.LogUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 以下部分。。。好像不需要。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
 * 使用时：
 *  getLoadingTargetView(
 *  fragment_layout 外层要再包一层
 *      //不然getRoot().getParent(android.R.content)为null
 *
 *
 * Created by Administrator on 2017/1/19.
 */

public abstract class AbsBaseFragment extends Fragment
                        implements ILoadingDelegate {
    private static final String TAG = "AbsBaseF";

    private VaryViewHelperController mVaryViewHelperController;

    //Binderknife取消绑定
    private Unbinder mUnbinder;
    //当前Fragment的View
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreateView");
//        if (mRootView == null) {
            mRootView = inflater.inflate(getFragmentLayoutID(), container, false);
//        }
        //在这里绑定Butterknife
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        onInitViewLoading();
//        showLoading("");
    }


    private void onInitViewLoading() {
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView(), getContext());
        }
    }

    /**
     * 留给外部的接口
     *      showLoading
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = "正在加载...";
        }
        toggleShowLoading(true, msg);
    }

    /**
     * 留给外部的接口
     *      hideLoading
     */
    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    /**
     * 真正显示Loading动画
     * @param toggle
     * @param msg
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for my_loading");
        }
        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * 得到 具体Fragment中 想要显示Loading动画的View(需要替换为Loading动画的View)
     * @return
     */
    protected abstract View getLoadingTargetView();

    /**
     * 得到 具体Fragment 的 layout
     * @return
     */
    protected abstract int getFragmentLayoutID();

    /**
     * 得到当前Fragment Layout的 contentView
     * @return
     */
    protected View getCurrentFragmentRootView() {
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除Butterknife的绑定(防止内存泄漏)
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
