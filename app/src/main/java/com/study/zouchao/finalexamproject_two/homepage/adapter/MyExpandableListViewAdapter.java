package com.study.zouchao.finalexamproject_two.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.service.DownloadService;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;




/**
 * 第二页 ExpandableListView的adapter
 * Created by Administrator on 2017/2/9.
 */

public class MyExpandableListViewAdapter
        extends BaseExpandableListAdapter
{
    private Context mContext;
    private List<String> mParent;
//    private List<FileInfo> mChild;
    private LinkedHashMap<String, List<FileInfo>> mMap = null;

    private IOnChildItemClickListener mListener;

    public MyExpandableListViewAdapter(Context mContext, LinkedHashMap<String, List<FileInfo>> mMap) {
        this.mContext = mContext;
        this.mParent = new ArrayList<>();
//        this.mChild = mChild;
        this.mMap = mMap;
        initParent();
    }


    //
    @Override
    public int getGroupCount() {
        return mMap.size();
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
//        List<String> parents = mMap.get;
        String key = mParent.get(groupPosition);
        return mMap.get(key).size();
    }
    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return mParent.get(groupPosition);
    }
    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = mParent.get(groupPosition);
        return (mMap.get(key).get(childPosition));
    }
    //
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    //
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_recommend_data_parent_item, parent, false);
        }
        TextView tvTitle = (TextView) convertView
                .findViewById(R.id.id_recommend_parent_title);
        tvTitle.setText(mParent.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_recommend_data_child_item, parent, false);
            holder = new ViewHolder();
            holder.tvChildTitle = (TextView) convertView.findViewById(R.id.id_data_child_title);
            holder.ivChild = (ImageView) convertView.findViewById(R.id.id_iv_data_leave);
//            holder.tvChildTime = (TextView) convertView.findViewById(R.id.id_data_child_time);
            holder.tvFileSize = (TextView) convertView.findViewById(R.id.id_tv_file_size);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FileInfo fileInfo = mMap.get(mParent.get(groupPosition)).get(childPosition);
        holder.tvChildTitle.setText(fileInfo.getFileName());
        holder.tvFileSize.setText("文件大小："+fileInfo.getTotalSizeByAdd()+"M");
        //开始下载点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(fileInfo.getUrl())) {
                    ToastUtils.showShort(mContext, "当前item不可用 不能下载文件");
                } else {
//                    addDownload(fileInfo);
                    if (mListener != null) {
                        mListener.onClick(fileInfo, groupPosition, childPosition);
                    }
                }
            }
        });
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initParent() {
        mParent.clear();
        Set<String> keys = mMap.keySet();
        for (String key : keys) {
            mParent.add(key);
        }

    }

    public void setOnChildItemClickListener(IOnChildItemClickListener listener) {
        mListener = listener;
    }

    public interface IOnChildItemClickListener {
        void onClick(FileInfo info, int parentPosition, int childPosition);
    }

    @Override
    public void notifyDataSetChanged() {
        initParent();
        super.notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView ivChild;
        TextView tvChildTitle;
        TextView tvChildTime;
        TextView tvFileSize;
    }
}