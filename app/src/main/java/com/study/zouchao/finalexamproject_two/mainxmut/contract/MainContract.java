package com.study.zouchao.finalexamproject_two.mainxmut.contract;


import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;

import org.jsoup.Connection;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */

public interface MainContract {
    /**
     * Model
     */
    interface IMainModel {
        void connScorePage(String url, LoginContract.ILoginModel.IConnListener listener);
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
    interface IMainPresenter {
        //解析登陆成功后的首页
        void parseHTML(String html);
        void onDestroyPresenter();
    }

    /**
     * View
     */
    interface IMainView {
        void setTitle(String title);
    }
}
