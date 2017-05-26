package com.study.zouchao.finalexamproject_two.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * 设备属性相关工具类
 * Created by tsy on 16/7/25.
 */
public class DeviceUtils {

    /**
     * 获取设备密度
     * @param context 全局context
     * @return 设备dpi
     */
    public static int getDeviceDpi(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 获取设备宽 高 单位像素
     * @param context 全局context
     * @return int[]
     *      [0] 设备宽(像素)
     *      [1] 设备高(像素)
     */
    public static int getDeviceSizeWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取设备宽 高 单位像素
     * @param context 全局context
     * @return int[]
     *      [0] 设备宽(像素)
     *      [1] 设备高(像素)
     */
    public static int getDeviceSizeHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 根据手机的分辨率从从dp转成为px(像素)
     * @param context 全局context
     * @param dpValue dp值
     * @return px像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context 全局context
     * @param pxValue px像素值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * android 获取当前手机型号
     * @param context 全局context
     * @return 当前手机型号
     */
    public static String getPhoneModel(Context context) {
        Build bd = new Build();
        return  bd.MODEL;
    }

    /**
     * android 获取当前手机品牌
     * @param context 全局context
     * @return 当前手机品牌
     */

    public static String getPhoneProduct(Context context) {
        Build bd = new Build();
        return  bd.BRAND;
    }
}
