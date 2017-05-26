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
}
