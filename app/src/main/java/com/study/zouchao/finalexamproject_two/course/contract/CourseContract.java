package com.study.zouchao.finalexamproject_two.course.contract;

import android.view.View;


import com.study.zouchao.finalexamproject_two.course.model.bean.CourseInfo;
import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;

import org.jsoup.Connection;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */

public interface CourseContract {
    /**
     * Model
     */
    interface ICourseModel {
        void connCoursePage(String url, Map<String, String> cookies, LoginContract.ILoginModel.IConnListener listener) throws UnsupportedEncodingException;

        void connTest(ITestListener listener);
        interface ITestListener {
            //返回每天的上课课程
            void onSuccess(List<CourseInfo>[] courseInfos);
        }

        /**
         * 回调接口
         */
        interface IConnListener {
            void onSuccess(Connection.Response response);
            void onFailure(Throwable throwable);
        }
    }



    /**
     * Presenter
     */
    interface ICoursePresenter {
        //点击fab按钮
        void clickFab(int currentWeek);

        void onDestroyPresenter();
    }

    /**
     * View
     */
    interface ICourseView  {
        //设置标题栏文字
        void setTitle(String title);
        //查询到课程结果后 显示在页面上
        void showCourse(String tableHtml);

        View findViewById(int id);
        //显示Loading动画
        void showLoading(boolean isShow);
    }
}
