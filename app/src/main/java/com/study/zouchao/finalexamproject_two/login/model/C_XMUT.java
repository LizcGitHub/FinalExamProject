package com.study.zouchao.finalexamproject_two.login.model;

import android.util.Log;

/**
 * Created by Administrator on 2016/11/29.
 */

public class C_XMUT {
    private static final String TAG = "C_XMUT";

    /**
     * 记住密码功能
     */
    public static final String sUser = "sUser";
    public static final String sPwd = "sPwd";

    //重定向前 厦门理工学院的网址 会自动重定向
    public static String XMUT_URL = "http://jxgl.xmut.edu.cn/";

    public static String LOGINED_URL = "";
    /**
     * Base_URL: http://210.34.214.105/
     */
    public static String BASE_URL = "";
    //验证码的url
    public static String CHECK_CODE_URL = "";

    /**
     * 打印所有url
     */
    public static void logAllUrl() {
        Log.d("loginAllUrl", "loginUrl...." + LOGINED_URL);
        Log.d("loginAllUrl", "baseUrl...." + BASE_URL);
        Log.d("loginAllUrl", "checkCodeUrl...." + CHECK_CODE_URL);
    }


    /**
     * MainXUMTActivity
     */
    /**
     * 登录成功 主页 key value
     */
    public static String LOGINED_SUCCESS_HTML = "LOGINED_SUCCESS_HTML";

    /**
     * 查询成绩
     *
     */
    public static String SCORE_URL = "";
    /**
     * 查询课表
     * http://210.34.214.87/xskbcx.aspx?xh=1421172164&xm=邹超&gnmkdm=N121603
     */
    public static String COURSE_URL = "";
    /**
     * 查询成绩、课表等
     *  需要请求转发
     *      需要一个Reference的地址
     *  http://210.34.214.87/xs_main.aspx?xh=1421172164
     */
    public static String REFER_URL = "";


    /**
     * 其他
     */
    /**
     * 一学期一共21周
     */
    public static int totalWeeksNum = 19;
    /**
     * 当前第17周：单周
     */
    public static int CURRENT_WEEK = 3;
    /**
     * 一天的课程数：一天12节课
     */
    public static final int DAY_COURSE_NUM = 12;
    /**
     * 一周七天
     */
    public static final int WEEK_DAY_NUM = 7;
    /**
     * 一节课的TextView高度
     */
    public static final int ITEM_SINGLE_HEIGHT = 200;

    /**
     * 当前学年
     */
    public static String CURRENT_YEAR = "";
    /**
     * 当前学期
     */
    public static String CURRENT_TERM = "";
    /**
     * 系统设置
     *
     */
    /**
     * 超时时间 40秒
     */
    public static final int TIME_OUT_SECONDS = 40000;
}
