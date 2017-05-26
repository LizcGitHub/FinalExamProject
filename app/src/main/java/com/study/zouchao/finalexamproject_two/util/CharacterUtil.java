package com.study.zouchao.finalexamproject_two.util;

/**
 * Created by Administrator on 2016/12/9.
 */

import java.io.UnsupportedEncodingException;

/**
 * 字符集类
 */
public class CharacterUtil {
    //解决浏览器url中文乱码
    public static String urlChineseStr(String inStr) {
        if (inStr == null) {
            return null;
        }
        try {
            System.out.print("inStr....." + inStr);
            return new String(inStr.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:09:51
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
