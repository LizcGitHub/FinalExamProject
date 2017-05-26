package com.study.zouchao.finalexamproject_two.login.contract;


import java.util.Map;
import org.jsoup.Connection;
/**
 * Created by Administrator on 2016/12/8.
 */

public interface LoginContract {
    /**
     * Model
     */
    interface ILoginModel {
        //得到cookies等
        void connXMUT(String url, IConnListener listener);
        void connAndgetCookies(String url, IConnListener listener);
        void getCheckCodeBytes(String url, Map<String, String> cookies, IConnListener listener);
        void login(String url, Map<String, String> params, Map<String, String> cookies, IConnListener listener);
        void onDestroyModel();
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
    interface ILoginPresenter  {
        //点击登录按钮：尝试登陆
        void attemptLogin(String username, String password, String checkCode);
        //点击验证码 重新加载验证码
        void reloadCheckCode();

        void onDestroyPresenter();
    }

    /**
     * View
     */
    interface ILoginView  {
        //加载验证码图片
        void loadCheckCode(byte[] bytes);
        //是否显示loading动画
        void showLoading(boolean isShow);
        //finish当前登录activity
        void finishCurrentActivity();
        //
        void action2MainActivity(Connection.Response response);
        //显示验证码图片 并且 验证码Loading不可见
        void showCheckCodeImage(boolean isShow);
        //登陆失败后提示提示框
        void showLoginErrorDialog(String promoteTv);

        void testView();
    }
}
