package com.study.zouchao.finalexamproject_two.logininterceptor.logininterface;

/**
 * Created by Administrator on 2016/11/23.
 */

public interface INeedLoginJudgeView {
    //要登陆判断的页面就重写该方法改为返回true
    boolean isCurrentPageNeedLoginJudge();
    //可传一个参数View
    void showLoginedView();
    void showNotLoginView();
}
