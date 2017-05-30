package com.study.zouchao.finalexamproject_two.util;

import android.content.Context;


import com.study.zouchao.finalexamproject_three.R;

import java.util.Random;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ColorUtil {
    /**
     * 基本颜色
     */
    public static int[] baseColors = new int[] {
            R.color.colorCourse1, R.color.colorCourse2, R.color.colorCourse3,
            R.color.colorCourse4, R.color.colorCourse5, R.color.orange,
            R.color.green, R.color.cyan
    };
    /**
     * 随机返回一个基本颜色
     * @return
     */
    public static int randomBaseColor(Context context) {
        Random random = new Random();
        return context.getResources().getColor(baseColors[random.nextInt(8)]);
    }
}
