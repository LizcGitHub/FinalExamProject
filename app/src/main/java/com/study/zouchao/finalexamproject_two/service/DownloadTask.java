package com.study.zouchao.finalexamproject_two.service;

import android.content.Context;
import android.util.Log;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.study.zouchao.finalexamproject_two.download.DownloadStatus;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadedDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.DownloadTaskDTO;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.UpdateProgressDTO;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.App;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.FileUtil;
import com.study.zouchao.finalexamproject_two.util.LogUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.io.File;



/**
 * Created by Administrator on 2017/2/12.
 */

public class DownloadTask {
    public static final String TAG = "DownloadTask";
    private FileInfo mFileInfo;
    private Context mContext;
    private HttpUtils mHttpUtils;
    //用于暂停
    private HttpHandler<File> mHandler;
    //由3个线程完成当前下载任务
    private static int sThreadSize = 3;

    private static final String PARENT_DIR = "_Study";
    private static final String SUB_DIR = "downloaded_file";

    private String mSaveAbsoultePath;

    //上一次更新进度的时间
    private long mLastUpdateTimeMillis = 0;
    //更新进度间隔（以毫秒为为单位）
    private static long sUpdateSpanTime = 1200;

    public DownloadTask(Context context, FileInfo fileInfo) {
        mContext = context;
        mFileInfo = fileInfo;
    }
    public void startDownload() {
        mHttpUtils = new HttpUtils();
        if (StringUtils.isEmpty(mFileInfo.getUrl())) {
            ToastUtils.showShort(mContext, TAG + "..mFileInfo缺少url");
            return;
        }
        //创建目标文件
        String targetPath = FileUtil.createFile(PARENT_DIR, SUB_DIR, mFileInfo.getFileName()).getAbsolutePath();
        mSaveAbsoultePath = targetPath;
        Log.i(TAG, "下载后保存的文件夹path:\n"+targetPath);
        mHandler = mHttpUtils.configRequestThreadPoolSize(sThreadSize)
                .download(mFileInfo.getUrl(),
                        targetPath, true, false, new RequestCallBack<File>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                onDownloadStart();
                            }
                            @Override
                            public void onLoading(long total, long current, boolean isUploading) {
                                super.onLoading(mFileInfo.getTotalSizeByAdd(), current, isUploading);
                                onDownloadLoading(total, current, isUploading);
                            }
                            @Override
                            public void onCancelled() {
                                super.onCancelled();
                                onDownloadCancelled();
                            }
                            @Override
                            public void onSuccess(ResponseInfo<File> responseInfo) {
                                onDownloadSuccess(responseInfo);
                            }
                            @Override
                            public void onFailure(HttpException e, String s) {
                                onDownloadFailure(e, s);
                            }
                        });
    }

    private void onDownloadStart() {
        //填入文件保存到本地的路径
        setSaveFilePath();
        //通知用户当前文件开始下载
//        ToastUtils.showShort(App.getAppContext(), mFileInfo.getFileName()+"："+"开始下载");
//        LogUtils.i(TAG, mFileInfo.getFileName()+"..onStart"+"\nthreadid"+Thread.currentThread().getId());
        //正在下载页面：显示图标为：开始下载
        DownloadingDAOImpl.saveCurrentFileDownloadStaus(mContext, mFileInfo, DownloadStatus.ING_DOWNLOAD);
        //更新正在下载界面
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_ONLY_UPDATE_UI, null));
        //显示通知
//        EventBusUtils.post(new EventBusEvent(DownloadingFragment.sSHOW_NOTIFICATION, mFileInfo));
    }

    private void setSaveFilePath() {
        mFileInfo.setSaveAbsoultePath(mSaveAbsoultePath);
    }

    private void onDownloadLoading(long total, long current, boolean isUploading) {
        //注：单位是B
//        Log.d("pdf总大小？？？", "总大小????"+mFileInfo.getTotalSizeByAdd());
//        Log.w("当前进度>>>>", current+"");
//        LogUtils.i(TAG, current * 1.0f / mFileInfo.getTotalSizeByAdd() * 100 +"..loading.."+mFileInfo.getFileName()+"\nthreadid"+Thread.currentThread().getId());
        //当前时间
        long nowTime = System.currentTimeMillis();
        //当前下载进度
        int currentProgress = (int) (current * 1.0f / mFileInfo.getTotalSizeByAdd() * 100);
        //写入数据库当前下载的进度
        DownloadingDAOImpl.saveCurrentFileDownloadingProgress(mContext, mFileInfo, currentProgress, mFileInfo.getTotalSizeByAdd(), current);
        //每1.2秒钟更新一次进度 TODO:不知道为什么更新UI会卡住
        if (nowTime - mLastUpdateTimeMillis > sUpdateSpanTime) {
            //设置该文件的总大小(TODO:事实上只要设置一次即可)
            mFileInfo.setTotalSize(mFileInfo.getTotalSizeByAdd());
            //发送广播 通知DownloadingFragment更新UI 更新进度条进度
            EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_UPDATE_DOWNLOADING_PROGRESS,
                    new UpdateProgressDTO(mFileInfo.getUrl(), currentProgress, mFileInfo.getTotalSizeByAdd(), current)));
            mLastUpdateTimeMillis = nowTime;
        }
    }

    private void onDownloadCancelled() {
        LogUtils.i(TAG, mFileInfo.getFileName()+"..onCancelled"+"\nthreadid"+Thread.currentThread().getId());
        //保存下载状态为尚未下载
        DownloadingDAOImpl.saveCurrentFileDownloadStaus(mContext, mFileInfo, DownloadStatus.NOT_DOWNLOAD);
        //更新正在下载界面
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_ONLY_UPDATE_UI, null));
        //让service中当前任务暂停
        removeDownloadingTask(mFileInfo.getFileName()+"..cancel");
    }

    private void onDownloadSuccess(ResponseInfo<File> responseInfo) {
        LogUtils.i(TAG, mFileInfo.getFileName()+"..onSuccess"+"\nthreadid"+Thread.currentThread().getId());
        //完成时最后更新一次进度   TODO:不再需要 直接更新已下载列表
        EventBusUtils.post(new UpdateProgressDTO(mFileInfo.getUrl(), 100));
        //让service中当前任务停止
        removeDownloadingTask(mFileInfo.getFileName()+"..success");
        //写入DB download
        DownloadedDAOImpl.addDownloadedTask(mContext, mFileInfo);
        //删除DB中 downloading中的当前任务
        DownloadingDAOImpl.deleteTaskFromDownloadingList(App.getAppContext(), mFileInfo);
        //通知 已下载ListView更新Ui
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_DOWNLOADED_SUCCESS_UPDATE_UI, null, "DownloadedSuccessUpdateUi"));
        //通知 正在下载ListView更新Ui
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_ONLY_UPDATE_UI, null));
        Log.d("success后............", responseInfo.result.toString());
        ToastUtils.showShort(App.getAppContext(), mFileInfo.getFileName()+"下载完成！！\n\t"+responseInfo.result.toString());
        //更新剩余ROM大小
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_UPDATE_ROM_UI, null));
    }

    private void onDownloadFailure(HttpException e, String s)  {
        //判断失败原因
        judgeDownloadFailure(e, s);
        //让service中当前任务停止
        removeDownloadingTask(mFileInfo.getFileName()+"..failure");
        //删除DB中 downloading中的当前任务
        DownloadingDAOImpl.deleteTaskFromDownloadingList(App.getAppContext(), mFileInfo);
        //通知 正在下载ListView更新Ui
        EventBusUtils.post(new EventBusEvent(
                EventBusEvent_C.EVENT_ONLY_UPDATE_UI, null));
        deleteCurFileFromSdCard();
    }

    private void deleteCurFileFromSdCard() {
        boolean rsDelete = FileUtil.deleteFile(mSaveAbsoultePath);
        if (rsDelete) {
            ToastUtils.showShort(App.getAppContext(), "下载失败、文件删除成功");
        } else {
            ToastUtils.showShort(App.getAppContext(), "下载失败、文件删除失败、");
        }
    }


    /**
     * 下载失败原因 判断
     * @param e
     * @param s
     */
    private void judgeDownloadFailure(HttpException e, String s) {
        e.printStackTrace();
//        LogUtils.i(TAG, mFileInfo.getFileName()+" download onFailure \t"+s+"\nthreadid"+Thread.currentThread().getId());
//        Log.i("难道错误提示在这？", e.getMessage()+"\n"+s+"\n"+e.getExceptionCode());
        switch (e.getExceptionCode()) {
            case 416 :
                //文件已经下载
                onFailureMaybeFileHasDownloadedCompletely();
                break;
            case 0 :
                onFailureUnknownHostException(e, s);
                break;
            default:
                ToastUtils.showShort(App.getAppContext(), "我也不懂为毛,反正你下载失败了,重新下吧~~~");
                break;
        }
    }

    /**
     * 下载失败：
     *  文件已经下载完成
     */
    private void onFailureMaybeFileHasDownloadedCompletely() {
        //写入DB download
        DownloadedDAOImpl.addDownloadedTask(mContext, mFileInfo);
        //删除DB中 downloading中的当前任务
        DownloadingDAOImpl.deleteTaskFromDownloadingList(App.getAppContext(), mFileInfo);
        //更新正在下载和已经下载的ListView
        ToastUtils.showShort(App.getAppContext(), mFileInfo.getFileName()+"：该文件可能已经下载完成、请到已下载页面查看");
    }


    /**
     * 下载失败：
     *   可能是没开网络
     * @param e
     * @param s
     */
    private void onFailureUnknownHostException(HttpException e, String s) {
        ToastUtils.showShort(App.getAppContext(), s+"\n\t请检查网络连接是否异常！！");
    }
    /**
     * 取消（暂停）下载
     */
    public void stopDownload() {
        if ( ! mHandler.isCancelled()) {
            //取消下载
            mHandler.cancel();
        }
    }

    /**
     * cancel 或 success 或 failure
     *  remove下载 service 中的task
     * @param msg
     */
    private void removeDownloadingTask(String msg) {
        //取消后remove service里正在下载任务集合当中的当前task
//        EventBusUtils.post();
        EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_REMOVE_DOWNLOAD_TASK,
                new DownloadTaskDTO(mFileInfo.getUrl(), msg)));
    }
}
