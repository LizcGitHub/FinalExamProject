package com.study.zouchao.finalexamproject_two.downloaddata.db;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/2/13.
 */

public class DownloadedDAOImpl {
    private static final String TAG = "DownloadedDAOImpl";
    private static final String DOWNLOADED_LIST = "DOWNLOADED_LIST";
    private static Gson sGson = new GsonBuilder().disableHtmlEscaping().create();


    /**
     * 添加一个新的正在下载task
     * @param context
     * @param fileInfo
     */
    public static void addDownloadedTask(Context context, FileInfo fileInfo) {
        List<FileInfo> downloadedList = getDownloadedListByBean(context);
//        Log.i(TAG, "添加前list.."+downloadedList.toString());
        //已经存在就别加咯
        if (downloadedList.contains(fileInfo)) {
//            Log.i(TAG, "已下载列表已经包含当前fileInfo");
            return;
        }
        //添加一个下载任务
        downloadedList.add(fileInfo);
//        Log.i(TAG, "添加后list.."+downloadedList.toString());
        //将list转换回json字符串
        String newStr = sGson.toJson(downloadedList);
        //先delete
        deleteDownloadedList(context);
        //重新写回DB
        SharedPreUtil.putString(context, DOWNLOADED_LIST, newStr);
//        Log.i(TAG, "添加到已下载列表:\n"+newStr);
    }

    /**
     * 删除正在下载列表
     * @param context
     */
    public static void deleteDownloadedList(Context context) {
        SharedPreUtil.removeKey(context, DOWNLOADED_LIST);
    }
    /**
     * 得到已经下载列表
     */
    public static List<FileInfo> getDownloadedListByBean(Context context) {
        //先get
        String strDownloadedList = getDownloadedListByString(context);
        List<FileInfo> list = null;
        if (StringUtils.isEmpty(strDownloadedList)) {
            //如果没有正在下载的任务 就new出一个新集合
            list = new ArrayList<>();
        } else {
//            Log.i(TAG, "已列表DB字符串。。\n"+strDownloadedList);
            Type collectionType = new TypeToken<List<FileInfo>>(){}.getType();
            list = (List<FileInfo>) sGson.fromJson(strDownloadedList, collectionType);
//            Log.i(TAG, "已下载列表DB..list。。\n"+list.toString());
        }
        return list;
    }

    public static String getDownloadedListByString(Context context) {
        return SharedPreUtil.getString(context, DOWNLOADED_LIST);
    }
}
