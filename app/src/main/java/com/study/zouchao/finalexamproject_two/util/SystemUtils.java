package com.study.zouchao.finalexamproject_two.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/2/5.
 */

public class SystemUtils {
    //当期的系统版本号
    public static int sCurrentSystemVersion = android.os.Build.VERSION.SDK_INT;

    /**
     * 获得系统时间 精确 到毫秒级别
     * @return
     */
    public static String getCurrentTimeByMi() {
        Calendar Cld = Calendar.getInstance();

        int YY = Cld.get(Calendar.YEAR) ;
        int MM = Cld.get(Calendar.MONTH)+1;
        int DD = Cld.get(Calendar.DATE);
        int HH = Cld.get(Calendar.HOUR_OF_DAY);
        int mm = Cld.get(Calendar.MINUTE);
        int SS = Cld.get(Calendar.SECOND);
        int MI = Cld.get(Calendar.MILLISECOND);

        return "" + YY + MM + DD + HH + mm + SS + MI;
    }


    /**
     * 得到 当前毫秒
     * @return
     */
    public static String getCurrentMi() {
        Calendar Cld = Calendar.getInstance();
        return Cld.get(Calendar.MILLISECOND) + "";
    }

    public static String getCurrentMinute() {
        Calendar Cld = Calendar.getInstance();
        return Cld.get(Calendar.MINUTE) + "";
    }

    public static String getCurrentSecond() {
        Calendar Cld = Calendar.getInstance();
        return Cld.get(Calendar.SECOND) + "";
    }
}
