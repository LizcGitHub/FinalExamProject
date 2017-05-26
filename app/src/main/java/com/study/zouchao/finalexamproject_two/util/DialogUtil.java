package com.study.zouchao.finalexamproject_two.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2016/10/15.
 */

public class DialogUtil {
    /**
     * 弹出 双选框
     * @param context
     * @param title
     * @param msg
     * @param positionListener
     * @param negativeListener
     */
    public static AlertDialog showAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener positionListener,
                                              DialogInterface.OnClickListener negativeListener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", positionListener)
                .setNegativeButton("取消", negativeListener)
                .setCancelable(false).create();
    }

    /**
     * 弹出 单选框
     * @param context
     * @param title
     * @param msg
     * @param positionListener
     * @param negativeListener
     */
    public static void showSingleAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener positionListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", positionListener)
                .setCancelable(false)
                .show();
    }


    /**
     * 弹出一个item对话框
     * @param context
     * @param title
     * @param items
     * @param listener
     */
    public static void showItemAlertDialog(Context context, String title,
                                           String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title);
//        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);
        builder.setItems(items, listener);
        //显示对话框
        builder.create().show();
    }


    /**
     * 弹出一个item对话框
     * @param context
     * @param title
     * @param items
     * @param listener
     */
    public static AlertDialog showItemAlertDialog(Context context, String title, String msg,
                                                  String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);
        builder.setPositiveButton("确定", listener);
//        builder.setNegativeButton("取消", null);
//        builder.setItems(items, listener);
        //显示对话框
        return builder.create();
    }
}
