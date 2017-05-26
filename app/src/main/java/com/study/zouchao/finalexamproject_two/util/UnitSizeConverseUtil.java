package com.study.zouchao.finalexamproject_two.util;

import java.text.DecimalFormat;

/**
 * 单位大小转换
 * Created by Administrator on 2017/2/15.
 */

public class UnitSizeConverseUtil {
    //保留两位小数
    private static DecimalFormat decimalFormat = new DecimalFormat("##.##");

    /**
     * B(字节byte) 转 MB
     *  以字符串的形式返回
     * @param BSize
     * @return
     */
    public static String Byte2MbByString(long BSize) {
        return decimalFormat.format(BSize / 1024.0 / 1024.0);
    }
}
