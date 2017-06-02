package com.study.zouchao.finalexamproject_two.util;

import java.security.PublicKey;

/**
 * Created by Administrator on 2017/5/19.
 */

public class EventBusEvent_C {

    /**
     * 取消后remove service里正在下载任务集合当中的当前task
     */
    public static final String EVENT_REMOVE_DOWNLOAD_TASK = "EVENT_REMOVE_DOWNLOAD_TASK";

    /**
     * DownloadedSuccessUpdateUi
     */
    public static final String EVENT_DOWNLOADED_SUCCESS_UPDATE_UI = "EVENT_DOWNLOADED_SUCCESS_UPDATE_UI";

    /**
     * EventBus 只是更新UI （只是notify）
     */
    public static final String EVENT_ONLY_UPDATE_UI = "ONEVENT_ONLY_UPDATE_UI";

    /**
     * EventBus 更新正在下载进度条的进度
     */
    public static final String EVENT_UPDATE_DOWNLOADING_PROGRESS = "UPDATE_DOWNLOADING_PROGRESS";

    /**
     * 更新当前手机剩余ROM大小
     */
    public static final String EVENT_UPDATE_ROM_UI = "EVENT_UPDATE_ROM_UI";

    /**
     * 用户旅游详情页
     */
    public static final String EVENT_GOTO_TRAVEL_DETAIL_FRAGMENT = "EVENT_GOTO_TRAVEL_DETAIL_FRAGMENT";

    /**
     * 控制main的toolbar的显示与隐藏
     */
    public static final String EVENT_TOGGLE_TOOLBAR = "EVENT_TOGGLE_TOOLBAR";

    /**
     * 检查当前第三个页面是哪个Fragment
     */
    public static final String EVENT_CHECK_CURRENT_THIRD_PAGE_FRAGMENT = "EVENT_CHECK_CURRENT_THIRD_PAGE_FRAGMENT";

    /**
     *  在MainActivity按下back键
     */
    public static final String EVENT_ON_BACK_IN_MAINACTIVITY = "EVENT_ON_BACK_IN_MAINACTIVITY";

    /**
     * 第三个页面(旅游)
     */
    public static final String EVENT_ON_HANDLE_BACK_EVENT = "EVENT_ON_HANDLE_BACK_EVENT";

    /**
     * 在mainActivity显示一个snackbar
     */
    public static final String EVENT_SHOW_SNACK_BAR_IN_MAINFACTIVITY = "EVENT_SHOW_SNACK_BAR_IN_MAINFACTIVITY";
}
