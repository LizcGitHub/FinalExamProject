package com.study.zouchao.finalexamproject_two.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 外存
 */

public class FileUtil {
    /***
     * 获得SD卡根目录的路径(String型返回)
     */
    public static String getSDPath() {
        File sdDir = null;
        //判断SD卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if ( sdCardExist ) {   //存在SD卡 获取SD卡的根目录
            sdDir = Environment.getExternalStorageDirectory(); //获取根目录
        }
        if (sdDir != null) {   //存在根目录(存在一种情况存在SD卡 但没有根目录)
            return sdDir.toString();
        } else {
            return "";
        }
    }
    /***
     * 获得SD卡根目录的路径(File型返回)
     */
    public static File getSDFile() {
        File sdDir = null;
        //判断SD卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if ( sdCardExist ) {   //存在SD卡 获取SD卡的根目录
            sdDir = Environment.getExternalStorageDirectory(); //获取根目录
        }
        if (sdDir != null) {   //存在根目录(存在一种情况存在SD卡 但没有根目录)
            return sdDir;
        } else {
            return null;
        }
    }

    /**
     *  在某文件夹内生成一个文件 并且返回该文件夹
     */
    public static File createFile(File parentFile, String childFileName) {
        if (parentFile == null)
            return null;
        File file = new File(parentFile, childFileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {e.printStackTrace();}
        return file;
    }


    /**
     * 一般用这个方法！！！！！！！
     * 创建一个file 用于保存文件
     * @param fileName
     * @return
     */
    public static File createFile(String rootDirName, String subDirName, String fileName) {
        String dir = getDirectory(rootDirName, subDirName);
        File dirFile = new File(dir);
        if (! dirFile.exists()) {
            dirFile.mkdirs();
        }
        File targetFile = new File(dir + "/" + fileName);
        try {
            //创建文件目录
            targetFile.createNewFile();
        } catch (IOException e) {e.printStackTrace();
            return null;
        }
        return targetFile;
    }

    /**
     * 得到完整的文件目录
     *  eg: rootDirName: BeautyListViewApp 根目录下有个BeautyListViewApp文件夹
     *      subDirName:  ImageCache        BeautyListViewApp文件夹内有个ImageCache文件夹
     */
    public static String getDirectory(String rootDirName, String subDirName) {
        return getSDPath() + "/" + rootDirName + "/" + subDirName;
    }

    public static String getFilePath(String rootDirName, String subDirName, String fileName) {
        return getSDPath()+"/"+rootDirName+"/"+subDirName+"/"+fileName;
    }

    /**
     * 得到某个url对应的文件名
     * @param suffix 文件后缀名(可选)
     *  eg: url:    http://static.yidianzixun.com//beauty//imgs//i_000SbqJy.jpg
     *      return: i_000SbqJy.jpg
     */
    public static String convertUrl2FileName(String url, String suffix) {
        String[] strs = url.split("/");
        if (suffix == null)
            suffix = "";
        return strs[strs.length - 1] + suffix;
    }
    /**
     *  保存图片
     */
    public static String savePhoto(Bitmap bitmap, String rootDirName, String subDirName, String photoName) {
        String localPath = null;
        if (getSDPath()!=null &&  !getSDPath().equals("")) {
            //先建一个文件夹
            File dir= new File(getDirectory(rootDirName, subDirName));
            String path = dir.getPath();
            Log.d("FileUtil", "这是绝对路径还是相对路径啊" + path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(photoFile);
                if (bitmap != null) {
                    //转换完成
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 5, out)) {
                        localPath = photoFile.getPath();
                        out.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        out = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }


    /*
     *
     * 删
     *
     */
    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //递归:删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param filePath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }
}
