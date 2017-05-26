package com.study.zouchao.finalexamproject_two.logininterceptor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.study.zouchao.finalexamproject_two.login.view.LoginActivity;


/**
 * 登录拦截器
 */

public class LoginInterceptor {
    //保存跳转信息的carrier的 key
    public static final String INVOKER_CARRIER = "INVOKER_CARRIER";
    /**
     * 需要登陆判断的界面的 尝试跳转
     * @param context
     * @param target     登录成功 跳转过去的activity(需要填入Activity的完整类名 隐性intent)
     *                      假如：登录成功后跳转回的是 当前activity
     *                               就填入 当前activity的完整类名
     * @param bundle     需要传给 登录成功 跳转过去的activity 的值
     */
    public static void attemptIntent(Context context, String target, Bundle bundle) {
        interceptor(context, target, bundle, null);
    }

    private static void interceptor(Context context, String target, Bundle bundle, Intent intent) {
        //存在要跳转的页面
        if (target != null) {
            //用一个bean来存储要跳转的内容
            LoginCarrier carrier = new LoginCarrier(target, bundle);
            //如果已经登陆
            if(LoginStatusUtil.getLoginStatus(context)) {
                //调用bean当中的方法直接跳转
                carrier.loginSuccessAndInvoke(context);
            } else {
                //没登陆就跳转到登陆页面吧
                if (intent == null) {
                    intent = new Intent(context, LoginActivity.class);
                }
                intent2LoginActivity(context, carrier, intent);
            }
        } else {
            //没有要跳转的页面
            Toast.makeText(context, "没有activity可以跳转", Toast.LENGTH_SHORT).show();
        }
    }
    //没有登陆的情况下。。跳转到登陆页面
    private static void intent2LoginActivity(Context context, LoginCarrier carrier, Intent intent) {
        //传入
        Bundle bundle = new Bundle();
        //将carrier 登陆信息bean 存到intent中  等到LoginActivity登陆成功后直接跳转过去
        bundle.putParcelable(INVOKER_CARRIER, carrier);
        intent.putExtras(bundle);
        //LogActivity新不新建随便啦。。设置了该值将不会onCreate而是onNewInent
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
