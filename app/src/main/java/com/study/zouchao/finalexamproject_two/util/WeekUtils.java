package com.study.zouchao.finalexamproject_two.util;

/**
 * Created by Administrator on 2016/12/23.
 */

public class WeekUtils {
    public static int Chinese2Int(String week) {
        if (week.equals("一"))
            return 1;
        if (week.equals("二"))
            return 2;
        if (week.equals("三"))
            return 3;
        if (week.equals("四"))
            return 4;
        if (week.equals("五"))
            return 5;
        if (week.equals("六"))
            return 6;
        //TODO:不知道教务系统是不是显示 "周日"
        if (week.equals("日"))
            return 7;
        return 0;
    }
}
