package com.study.zouchao.finalexamproject_two.login.loginStatus;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;


/**
 * 保存账号密码到本地
 *      记住密码功能
 */

public class LoginStatusUtil {
    /**
     *  记住密码
     *
     */
    public static void saveUserAndPwd(Context context, String user, String pwd) {
        SharedPreUtil.putString(context, C_XMUT.sUser, user);
        SharedPreUtil.putString(context, C_XMUT.sPwd, pwd);
    }
//

    /**
     * 退出登陆
     *      清空所有已存在的状态
     *      ps(清除用户信息)
     */
    public static void clearUserAndPwd(Context context) {
        SharedPreUtil.removeKey(context, C_XMUT.sUser);
        SharedPreUtil.removeKey(context, C_XMUT.sPwd);
    }
//
    /**
     *  更新已登陆状态
     */
    public static void updateUserAndPwd(Context context, String user, String pwd) {
        //在更新之前 清除所有状态
        clearUserAndPwd(context);
        //重新写入
        saveUserAndPwd(context, user, pwd);
    }
}
