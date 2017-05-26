package com.study.zouchao.finalexamproject_two.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.DownloadTaskDTO;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLWarning;
import java.util.LinkedHashMap;




/**
 * Created by Administrator on 2017/2/12.
 */

public class DownloadService extends Service {
    public static final String TAG = "DownloadS";
    //开始下载
    public static final String ACTION_START = "ACTION_START";
    //暂停下载
    public static final String ACTION_STOP = "ACTION_STOP";
    //用一个集合来管理正在下载的任务 (正在运行下载的任务) DownloadTask  K..url, V..DownloadTask
    private LinkedHashMap<String, DownloadTask> mDownloadingTasks;
    //最大允许同时下载的任务数量
    private static final int MAX_CONCURRENCY = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        EventBusUtils.register(this);
    }

    /**
     */
    private void init() {
        mDownloadingTasks = new LinkedHashMap<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_START :
                actionStart(intent);
                break;
            case ACTION_STOP  :
                actionStop(intent);
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * intent 为 ACTION_START
     * @param intent
     */
    private void actionStart(Intent intent) {
        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");

        DownloadTask task = new DownloadTask(this, fileInfo);
        if (mDownloadingTasks.containsKey(fileInfo.getUrl())) {
//            ToastUtils.showShort(this, "该任务已经启动！！！");
//            LogUtils.i(TAG, "正在下载集合中已经包含该任务!!!!!!!");
            return;
        }
        //最多只允许同时3个文件一起下载
        if (mDownloadingTasks.size() + 1 > MAX_CONCURRENCY) {
            ToastUtils.showShort(this, "最多只允许2个文件同时下载");
            return;
        }
        //加入下载任务集合 (先加入再开始下载)
        mDownloadingTasks.put(fileInfo.getUrl(), task);
        //开始下载
        task.startDownload();
//        Log.i(TAG, "action: start\n"+fileInfo.toString());
    }

    /**
     * Intent 为 ACTION_STOP
     * @param intent
     */
    private void actionStop(Intent intent) {
        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
        if ( ! mDownloadingTasks.containsKey(fileInfo.getUrl())) {
            ToastUtils.showShort(this, TAG+"..无法暂停，并没有当前任务（该任务已经暂停）");
            return;
        }
        //暂停下载
        mDownloadingTasks.get(fileInfo.getUrl()).stopDownload();
    }

    /**
     * 任务以下状态会调用该方法
     * cancel(pause) || success || failure
     * @param dto
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventRemoveDownloadingTask(DownloadTaskDTO dto) {
//        if ( ! mDownloadingTasks.containsKey(dto.url) ) {
//            //集合中没有该任务
//            return;
//        }
//        //再次确认停止下载 (被多线程玩怕了。。。。。。。)
//        mDownloadingTasks.get(dto.url).stopDownload();
//        mDownloadingTasks.remove(dto.url);
//        //
//        startNextDownloadingTask();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_REMOVE_DOWNLOAD_TASK :
                removeDownloadingTask(event);
                break;
        }
    }

    private void removeDownloadingTask(EventBusEvent event) {
        DownloadTaskDTO dto = (DownloadTaskDTO) event.getObj();
        if ( ! mDownloadingTasks.containsKey(dto.url) ) {
            //集合中没有该任务
            return;
        }
        //再次确认停止下载 (被多线程玩怕了。。。。。。。)
        mDownloadingTasks.get(dto.url).stopDownload();
        mDownloadingTasks.remove(dto.url);
        //
        startNextDownloadingTask();
    }

    /**
     * 完成一个下载任务后查看Map中是否还有其他下载任务
     *  如果有就开启
     */
    private void startNextDownloadingTask() {
        //TODO:从DB中取出下一个下载任务
    }

    /**
     * 没鸟用
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 启动任务
     * @param context
     * @param fileInfo
     */
    public static void actionStartAddDownload(Context context, FileInfo fileInfo) {
        //先写入DB 正在下载列表
        DownloadingDAOImpl.addDownloadingTask(context, fileInfo);
        //通知 service开始 下载
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra("fileInfo", fileInfo);
        context.startService(intent);   //启动service
//        ToastUtils.showShort(context, fileInfo.getFileName()+"：已加入下载任务列表");
    }

    /**
     * 暂停下载
     * @param context
     * @param fileInfo
     */
    public static void actionStopDownload(Context context, FileInfo fileInfo) {
        //通知 service停止下载
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(DownloadService.ACTION_STOP);
        intent.putExtra("fileInfo", fileInfo);
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }
}
