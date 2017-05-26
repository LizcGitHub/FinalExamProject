package com.study.zouchao.finalexamproject_two.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/12/8.
 */

public class FileUtils {
    private final static String FOLDER_NAME = "XMUT_Login";
    private final static String SUB_FOLDER_DIR = "CheckCode";     //文件夹名
    private final static String WHOLESALE_CONV = ".cach";  //缓存后缀名
    /*将图片存入文件缓存*/
    public static void saveBitmap(Bitmap bitmap, String imgName) {
        if (bitmap == null)
            return;
        String fileName = convertUrlToFileName(imgName);
        String dir = getDirectory();
        File dirFile = new File(dir);
        if ( ! dirFile.exists() )
            dirFile.mkdirs();              //若文件夹不存在 创建文件夹
        File file = new File(dir + "/" + fileName); //得到完整的文件目录
        try {
            file.createNewFile();                //如果文件已存在(图片已缓存到本地) 不会重复缓存
            Log.d("FileUtils", file.getAbsolutePath());
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);   //100%表示不压缩(事实上JPEG依然会压缩)
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
    }
    /*得到完整的文件目录*/
    private static String getDirectory() {
        String dir = getSDPath() + "/"+FOLDER_NAME+"/"+SUB_FOLDER_DIR;        //根目录下的CACH_DIR 文件夹
        return dir;
    }
    /*将url转成文件名*/
    private static String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        //这里文件后缀名视情况而定需不需要更改
//        return strs[strs.length - 1] + WHOLESALE_CONV;  //eg: http://static.yidianzixun.com//beauty//imgs//i_000SbqJy.jpg ----->i_000SbqJy.jpg.cach
        return strs[strs.length - 1];
    }
    /*获取SD卡的路径(根目录路径)*/
    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  //判断SD卡是否存在
        if ( sdCardExist ) {   //存在SD卡 获取SD卡的根目录
            sdDir = Environment.getExternalStorageDirectory(); //获取根目录
        }
        if (sdDir != null) {   //存在根目录
            return sdDir.toString();
        } else {
            return "";
        }
    }
}
