package com.study.zouchao.finalexamproject_two.util;

import android.util.Log;

/**
 * 可用于显示过长的Log日志
 * Created by Administrator on 2017/1/19.
 */

public class LogLongUtil {
    public static void logI(String tag, String content) {
        int p = 2048;
        long length = content.length();
        if (length < p || length == p)
            Log.i(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, content);
        }
    }

    /**
     * Log.d
     * @param tag
     * @param content
     */
    public static void logD(String tag, String content) {
        int p = 2048;
        long length = content.length();
        if (length < p || length == p)
            Log.d(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                Log.d(tag, logContent);
            }
            Log.d(tag, content);
        }
    }
}
