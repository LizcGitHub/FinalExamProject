package com.study.zouchao.finalexamproject_two.util;

/**
 * 数字Utils
 */

public class NumUtils {

    /**
     * 将阿拉伯数字转换为中文数字
     * @param num
     * @return
     */
    public static String getNum2Chinese(String num) {
        String chineseNum = "";
        switch (num) {
            case "1" :
                chineseNum = "一";
                break;
            case "2" :
                chineseNum = "二";
                break;
        }
        return chineseNum;
    }
}
