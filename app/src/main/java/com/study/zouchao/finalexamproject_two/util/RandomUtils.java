package com.study.zouchao.finalexamproject_two.util;

import java.util.Random;

/**
 * Created by Administrator on 2017/2/5.
 */

public class RandomUtils {

    public static Random mRandom = new Random();

    /**
     *  产生一个图片文件名
     *    当前的时间戳 + 四位数随机数
     * @param expandName  扩展名 eg: .jpg
     * @return
     */
    public static String getImgFilename(String expandName) {
        return "IMG_" + SystemUtils.getCurrentTimeByMi() + "_" + (mRandom.nextInt(9999)) + expandName;
    }

    /**
     * 生成随机的id值
     *  当前分 + 当前秒 + 五位随机数
     * @return
     */
    public static int getRandomIdByInt() {
        int value = Integer.valueOf(SystemUtils.getCurrentMinute()
                +SystemUtils.getCurrentSecond()+mRandom.nextInt(99999));
        System.out.println("生成的随机id..."+value+"");
        return value;
    }
}
