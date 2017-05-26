package com.study.zouchao.finalexamproject_two.logininterceptor;

import android.content.Context;

import com.google.gson.Gson;
import com.study.zouchao.finalexamproject_two.login.model.bean.User;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;


/**
 * 登陆状态
 * Created by Administrator on 2016/11/5.
 */

public class LoginStatusUtil {
    /**
     *  保存已登陆状态
     *      true
     */
    public static void saveLoginedStatus(Context context) {
        SharedPreUtil.putBoolean(context, C.IS_LOGIN, true);
    }
//
    /**
     *  保存已登录的User信息
     *      以User json字符串的方式
     */
    public static void saveLoginedUserInfo(Context context, User user) {
        SharedPreUtil.putString(context, C.USER, new Gson().toJson(user));
    }
//
    /**
     *  获取登陆状态
     *      从SharePreferences中取出登录状态
     */
    public static boolean getLoginStatus(Context context) {
        return SharedPreUtil.getBoolean(context, C.IS_LOGIN);
    }

    /**
     * 退出登陆
     *      清空所有已存在的状态
     *      ps(清除用户信息)
     */
    public static void exitLogin(Context context) {
//        SharedPreUtil.clearAll(context);
        SharedPreUtil.removeKey(context,C.USER);
        SharedPreUtil.removeKey(context,C.IS_LOGIN);
    }
//
    /**
     *  更新已登陆状态
     */
    public static void updateLoginedStatus(Context context, User user) {
        //在更新之前 清除所有状态
        exitLogin(context);
        //重新写入已登陆状态
        saveLoginedStatus(context);
        //重新写入
        saveLoginedUserInfo(context, user);
    }

    /**
     * 得到存储在SharePre中的User全部User信息
     *      返回的是json格式
     */
    public static String getLoginData(Context context) {
        return SharedPreUtil.getString(context, C.USER);
    }

    /**
     * 得到存储在SharePre中的User全部User信息
     *      返回的是bean的形式
     * @param context
     * @return
     */
    public static User getLoginDataByBean(Context context) {
        User userBean = new Gson().fromJson(getLoginData(context), User.class);
        if (userBean != null) {
            return userBean;
        } else {
            return null;
        }
    }
//
//    /**
//     * 从保存到本地的User中获取头像的uri
//     */
//    public static String getHeadPicUriFromSharePreUse(Context context) {
//        String jsonUser = getLoginData(context);
//        if (jsonUser != null) {
//            User userBean = new Gson().fromJson(getLoginData(context), User.class);
//            return userBean.getData().getUserHeadImage();
//        } else {
//            return "";
//        }
//    }
}
