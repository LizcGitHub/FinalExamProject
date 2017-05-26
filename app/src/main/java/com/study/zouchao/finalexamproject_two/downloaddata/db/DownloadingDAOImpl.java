package com.study.zouchao.finalexamproject_two.downloaddata.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.study.zouchao.finalexamproject_two.download.DownloadStatus;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;



/**
 * Created by Administrator on 2017/2/13.
 */

public class DownloadingDAOImpl {
    private static final String TAG = "DownloadingDAOImpl";
    private static final String DOWNLOADING_LIST = "DOWNLOADING_LIST";
    private static Gson sGson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * 添加一个新的正在下载task
     * @param context
     * @param fileInfo
     */
    public static void addDownloadingTask(Context context, FileInfo fileInfo) {
        List<FileInfo> downloadingList = getDownloadingListByBean(context);
        //已经存在就别加咯
        if (downloadingList.contains(fileInfo)) {
//            Log.i(TAG, "正在下载列表已经包含当前fileInfo");
            return;
        }
        //添加一个下载任务
        downloadingList.add(fileInfo);
        //更新正在下载列表
        updateDownloadingTaskList(context, downloadingList);
    }

    /**
     * 删除正在下载列表
     * @param context
     */
    public static void deleteDownloadingList(Context context) {
        SharedPreUtil.removeKey(context, DOWNLOADING_LIST);
    }

    /**
     * 下载失败后 删除正在下载
     * @param context
     * @param fileInfo
     */
    public static void deleteTaskFromDownloadingList(Context context, FileInfo fileInfo) {
        List<FileInfo> downloadingList = getDownloadingListByBean(context);
        //删除当前任务
//        Log.i(TAG, "delete迭代前\n" + downloadingList.toString());
        ListIterator<FileInfo> it = downloadingList.listIterator();
        while (it.hasNext()) {
            FileInfo info = it.next();
            if (info.getUrl().equals(fileInfo.getUrl())) {
                it.remove();
            }
        }
//        Log.i(TAG, "delete迭代后\n" + downloadingList.toString());
        //更新正在下载列表
        updateDownloadingTaskList(context, downloadingList);
    }

    /**
     * 更新正在下载列表
     * @param context
     * @param newList
     */
    public static void updateDownloadingTaskList(Context context, List<FileInfo> newList) {
        //将list转换回json字符串
        String newStr = sGson.toJson(newList);
        Log.i("不能继续>>update", newStr);
        //先delete
        deleteDownloadingList(context);
        //重新写回DB
        SharedPreUtil.putString(context, DOWNLOADING_LIST, newStr);
//        Log.i(TAG, "更新到正在下载列表:\n"+newStr);
    }

    /**
     * 得到正在下载列表 以字符串的形式返回
     * @param context
     * @return
     */
    public static String getDownloadingListByString(Context context) {
        return SharedPreUtil.getString(context, DOWNLOADING_LIST);
    }

    /**
     * 得到正在下载列表 以List的形式返回
     * @param context
     * @return
     */
    public static List<FileInfo> getDownloadingListByBean(Context context) {
        //先get
        String strDownloadingList = getDownloadingListByString(context);
        List<FileInfo> list = null;
        if (StringUtils.isEmpty(strDownloadingList)) {
            //如果没有正在下载的任务 就new出一个新集合
            list = new ArrayList<>();
        } else {
//            Log.i(TAG, "正在下载列表DB字符串。。\n"+strDownloadingList);
            Type collectionType = new TypeToken<List<FileInfo>>(){}.getType();
            list = (List<FileInfo>) sGson.fromJson(strDownloadingList, collectionType);
//            Log.i(TAG, "正在下载列表DB..list。。\n"+list.toString());
        }
        return list;
    }


    /**
     * 保存某个正在下载文件的 下载进度
     * @param context
     * @param fileInfo
     * @param currentProgress
     */
    public static void saveCurrentFileDownloadingProgress(Context context, FileInfo fileInfo, int currentProgress, long totalSize, long currentSize) {
        List<FileInfo> list = getDownloadingListByBean(context);
        ListIterator<FileInfo> it = list.listIterator();
        while (it.hasNext()) {
            FileInfo info = it.next();
            if (info.getUrl().equals(fileInfo.getUrl())) {
                //改变下载进度
                info.setFinished(currentProgress)
                    .setTotalSize(totalSize)
                    .setCurrentSize(currentSize);
            }
        }
        //更新正在下载列表
        updateDownloadingTaskList(context, list);
    }

    /**
     * 保存某个正在下载文件的 信息
     * @param context
     * @param fileInfo
     * @param currentProgress
     */
    public static void saveCurrentFileDownloadingInfo(Context context, FileInfo fileInfo,
                                                      int currentProgress, long totalSize,
                                                      long currentSize, int currentDownloadStatus) {
        List<FileInfo> list = getDownloadingListByBean(context);
        ListIterator<FileInfo> it = list.listIterator();
        while (it.hasNext()) {
            FileInfo info = it.next();
            if (info.getUrl().equals(fileInfo.getUrl())) {
                //改变下载进度
                info.setFinished(currentProgress)
                        .setTotalSize(totalSize)
                        .setCurrentSize(currentSize)
                        .setCurrentDownloadStatus(currentDownloadStatus);
            }
        }
        //更新正在下载列表
        updateDownloadingTaskList(context, list);
    }

    /**
     * 当程序启动时
     *   所有downloading表当中的task的下载状态设为 2(已暂停)
     */
    public static void initAllDownloadingTaskStatus(Context context) {
        List<FileInfo> list = getDownloadingListByBean(context);
        for (FileInfo info : list) {
            info.setCurrentDownloadStatus(DownloadStatus.Pause_DOWNLOAD);
        }
        //写回DB中
        updateDownloadingTaskList(context, list);
    }

    /**
     * 保存某个文件的下载状态
     * @param context
     * @param fileInfo
     * @param downloadStaus
     */
    public static void saveCurrentFileDownloadStaus(Context context, FileInfo fileInfo, int currentDownloadStaus) {
        List<FileInfo> list = getDownloadingListByBean(context);
        ListIterator<FileInfo> it = list.listIterator();
        while (it.hasNext()) {
            FileInfo info = it.next();
            if (info.getUrl().equals(fileInfo.getUrl())) {
                //改变下载进度
                info.setCurrentDownloadStatus(currentDownloadStaus);
            }
        }
        //更新正在下载列表
        updateDownloadingTaskList(context, list);
    }
}
