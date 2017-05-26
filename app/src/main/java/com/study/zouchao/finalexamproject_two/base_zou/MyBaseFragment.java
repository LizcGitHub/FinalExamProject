package com.study.zouchao.finalexamproject_two.base_zou;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.zouchao.finalexamproject_two.base_zou.absBase.AbsBaseFragment;


/**
 * Created by Administrator on 2017/1/19.
 */

public abstract class MyBaseFragment extends AbsBaseFragment {

    @Override
    public void showError(String msg) {}

    @Override
    public void showException(String msg) {}

    @Override
    public void showNetError() {}

    @Override
    public void showEmpty() {}

    /*
     * 生命周期
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

    }


    @Override
    public void onResume() {
        super.onResume();
        //判断登陆 状态
        judgeLoginStatusAndShowUI();
    }

    /*
     * 登陆判断
     */
    //判断登陆状态并且显示响应的UI
    private void judgeLoginStatusAndShowUI() {
        //不需要判断登陆 就直接返回即可
        if ( ! isCurrentPageNeedLoginJudge()) {
            return;
        }
//        //如果需要 进行 登陆 状态判断
//        if (LoginStatusUtil.getLoginStatus(getActivity())) {
//            showLoginedUI();
//        } else {
//            showNotLoginUI();
//        }
    }

    //要登陆判断的页面就重写该方法改为返回true
    protected boolean isCurrentPageNeedLoginJudge() {return false;}
    //显示登陆 后的界面
    protected void showLoginedUI() {}
    //显示未登陆 界面
    protected void showNotLoginUI() {}
}
