package com.study.zouchao.finalexamproject_two.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  正则表达式匹配类
 */

public class RegexUtils {

    /**
     * 字符串正则匹配
     * @param inputStr
     * @param regex
     * @return
     */
    public static Matcher regexMatcher(String inputStr, String regex) {
        //先根据正则表达式字符串生成一个Pattern对象
        Pattern pattern = Pattern.compile(regex);
        //再用这个Pattern对象匹配要匹配的字符串
        return pattern.matcher(inputStr);
    }

    /**
     * 正则匹配
     */
    public static boolean isValid(String inputStr, String regex) {
        return inputStr.matches(regex);
    }


    /**
     * 去掉&nbsp;
     */
    public static String removeSpaceHtml(String inStr) {
        if (StringUtils.isEmpty(inStr)) return "";
        String rs = inStr.replace("&nbsp;", "");
//        Log.d("去掉空格", rs);
        return rs;
    }

    /**
     * 去掉&nbsp;
     */
    public static String removeBrHtml(String inStr) {
        if (StringUtils.isEmpty(inStr)) return "";
        String rs = inStr.replace("<br>", "");
//        Log.d("去掉<br>", rs);
        return rs;
    }
}
