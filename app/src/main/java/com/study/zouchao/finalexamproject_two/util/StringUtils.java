package com.study.zouchao.finalexamproject_two.util;

/**
 * 字符串相关方法
 * Created by tsy on 16/8/15.
 */
public class StringUtils {

    /**
     * 是否为空
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static Boolean isEmpty(String str) {
        if(str == null || str.equals("")) {
            return true;
        }

        return false;
    }

    /**
     * 省略字符串
     * @param preStr
     * @param maxLen
     * @return
     */
    public static String strOmit(String preStr, int maxLen) {
        if (isEmpty(preStr) || maxLen <= 0)    return "";
        if (preStr.length() > maxLen)
            return preStr.substring(0, maxLen) + "..";
        return preStr;
    }
}
