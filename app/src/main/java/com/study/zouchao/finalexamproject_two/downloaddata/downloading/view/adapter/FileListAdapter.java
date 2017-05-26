package com.study.zouchao.finalexamproject_two.downloaddata.downloading.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.download.DownloadStatus;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.service.DownloadService;
import com.study.zouchao.finalexamproject_two.util.UnitSizeConverseUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 正在下载
 *   文件列表适配器
 * Created by Administrator on 2017/2/9.
 */

public class FileListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FileInfo> mFileList = null;
    //K..url, V..FileInfo
    private LinkedHashMap<String, FileInfo> mFileMap = null;
    public FileListAdapter(Context context, List<FileInfo> fileList) {
        mContext = context;
        mFileList = fileList;
        mFileMap = new LinkedHashMap<>();
        init();
    }
    private void init() {
        //遍历List 生成Map
        for (FileInfo fileInfo : mFileList) {
            mFileMap.put(fileInfo.getUrl(), fileInfo);
        }
    }
    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            //加载视图
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_downloading, viewGroup, false);
            //获得布局中的控件
            holder = new ViewHolder();
            holder.tvFile = (TextView) view.findViewById(R.id.id_tv_file);
            holder.btnStart = (Button) view.findViewById(R.id.id_btn_start);
            holder.tvStatus = (TextView) view.findViewById(R.id.id_tv_staus);
            holder.btnStop = (Button) view.findViewById(R.id.id_btn_pause);
            holder.pbFile = (ProgressBar) view.findViewById(R.id.id_progress_bar);
            holder.tvProgress = (TextView) view.findViewById(R.id.id_tv_progress);
            view.setTag(holder);
        }
        final FileInfo fileInfo = mFileList.get(position);
        holder = (ViewHolder) view.getTag();
        holder.tvFile.setText(fileInfo.getFileName());
        holder.pbFile.setMax(100);
        if (fileInfo.getCurrentDownloadStatus()== DownloadStatus.NOT_DOWNLOAD
                || fileInfo.getCurrentDownloadStatus()==DownloadStatus.Pause_DOWNLOAD) {
            holder.btnStart.setVisibility(View.VISIBLE);
            holder.btnStop.setVisibility(View.GONE);
        }
        if (fileInfo.getCurrentDownloadStatus()==DownloadStatus.ING_DOWNLOAD) {
            holder.btnStart.setVisibility(View.GONE);
            holder.btnStop.setVisibility(View.VISIBLE);
        }
        //设置文件的进度
        holder.pbFile.setProgress(fileInfo.getFinished());
        //设置文件的进度 eg: 14.46M/47.22M
        holder.tvProgress.setText(
                UnitSizeConverseUtil.Byte2MbByString(fileInfo.getCurrentSize())+"M/"+
                UnitSizeConverseUtil.Byte2MbByString(fileInfo.getTotalSize())+"M");
        holder.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.actionStartAddDownload(mContext, fileInfo);
//                //通知 service开始 下载
//                Intent intent = new Intent(mContext, DownloadService.class);
//                intent.setAction(DownloadService.ACTION_START);
//                Log.i("不能继续>>点击继续", fileInfo.toString());
//                intent.putExtra("fileInfo", fileInfo);
//                mContext.startService(intent);   //启动service
            }
        });
        holder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadService.actionStopDownload(mContext, fileInfo);
//                Toast.makeText(mContext, "stop.."+ fileInfo.getFileName(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    /**
     * 更新列表项中的进度条
     * @param fileId  文件id用于标识是哪个下载任务
     * @param progress 进度值
     */
    public void updateProgress(String url, int progress, long totalSize, long currentSize) {
        Log.d("FileListAdapter", "map:\n"+ mFileMap.toString());
//        FileInfo fileInfo = mFileMap.get(url);
        FileInfo fileInfo = null;
        for (FileInfo info : mFileList) {
            if (info.getUrl().equals(url)) {
                fileInfo = info;
            }
        }
        if (fileInfo == null) {
            Log.d("FileListAdapter", "好像问题出在这fileInfo为null");
            return;
        } else {
            Log.d("FileListAdapter", "奇怪。。好像不为空啊");
        }
        //更改List中的fileInfo的完成进度finished值
        fileInfo.setFinished(progress)
                //设置文件总大小
                .setTotalSize(totalSize)
                //设置当前下载的大小
                .setCurrentSize(currentSize);
        Log.d("FileListAdapter", "设置后fileInfo\n"+fileInfo.toString());
        //通知ListView数据修改
        notifyDataSetChanged();
    }

    /**
     * 定义成static 保证整个程序中 这个类 只会被加载一次
     *   省的每次创建FileListAdapter都 加载一次ViwHolder
     */
    static class ViewHolder {
        TextView tvFile, tvStatus, tvProgress;
        Button btnStart, btnStop;
        ProgressBar pbFile;
    }
}
