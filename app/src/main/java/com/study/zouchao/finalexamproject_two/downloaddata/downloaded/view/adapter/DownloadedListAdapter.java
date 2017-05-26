package com.study.zouchao.finalexamproject_two.downloaddata.downloaded.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.app.readpdfdemo.PdfActivity;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;
import com.study.zouchao.finalexamproject_two.util.UnitSizeConverseUtil;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/2/14.
 */

public class DownloadedListAdapter extends BaseAdapter {
    private Context mContext;
    private List<FileInfo> mFileList = null;
    //K..url, V..FileInfo
    private LinkedHashMap<String, FileInfo> mFileMap = null;
    public DownloadedListAdapter(Context context, List<FileInfo> fileList) {
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
            holder = new ViewHolder();
            //加载视图
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_downloaded, viewGroup, false);
            holder.tvFileName = (TextView) view.findViewById(R.id.id_tv_downloaded_filename);
            holder.tvTotalSize = (TextView) view.findViewById(R.id.id_tv_total_size);
            holder.btnOpen = (Button) view.findViewById(R.id.id_btn_open);
            //获得布局中的控件
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        final FileInfo fileInfo = mFileList.get(position);
        holder.tvFileName.setText(fileInfo.getFileName());
        //eg: 48.45M
        holder.tvTotalSize.setText(UnitSizeConverseUtil.Byte2MbByString(fileInfo.getTotalSize())+"M");
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfActivity.actionStartPdfActivity(mContext, fileInfo.getSaveAbsoultePath());
            }
        });
        return view;
    }


    /**
     * 定义成static 保证整个程序中 这个类 只会被加载一次
     *   省的每次创建FileListAdapter都 加载一次ViwHolder
     */
    static class ViewHolder {
        //已下载文件名、文件大小
        TextView tvFileName, tvTotalSize;
        Button btnOpen;
    }
}
