package com.study.zouchao.finalexamproject_two.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/10.
 */

public class BitmapUtil {
    /**
     * 由Bitmap获得该图片的二进制图片流
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
    /**
     * 由Bitmap获得该图片的Base64
     */
    public static String getImgByBase64(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        byte[] bytes = getBitmapByte(bitmap);
        return byte2Base64(bytes);
    }
    /**
     *
     * @param bytes
     * @return
     */
    public static String byte2Base64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    /**
     * 由二进制流得到Bitmap
     * @param temp
     * @return
     */
    public static Bitmap getBitmapFromByte(byte[] temp){
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }



}
