package com.study.zouchao.finalexamproject_two.downloaddata.downloading.presenter;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.contract.IDownloadingContract;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.UpdateProgressDTO;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.view.adapter.FileListAdapter;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;

import java.util.List;


/**
 * Created by Administrator on 2017/2/12.
 */

public class DownloadingPresenter implements IDownloadingContract.IDownloadingPresenter {
    private IDownloadingContract.IDownloadingView mView;
    private Context mContext;
    private List<FileInfo> mFileList = null;
    private FileListAdapter mAdapter = null;

    public DownloadingPresenter(Context context, IDownloadingContract.IDownloadingView view) {
        mView = view;
        mContext = context;
        init();
    }
    private void init() {
        //从DB中取出正在下载的任务
        mFileList = DownloadingDAOImpl.getDownloadingListByBean(mContext);
        mAdapter = new FileListAdapter(mContext, mFileList);
        mView.setListViewAdapter(mAdapter);
    }


    @Override
    public void updateProgress(UpdateProgressDTO dto) {
        mAdapter.updateProgress(dto.url, dto.progress, dto.total, dto.current);
    }

    @Override
    public void updateDownloadingListViewItemCount() {
        //TODO:Loading动画
        //从DB中取出正在下载的任务
        mFileList.clear();
        mFileList.addAll(DownloadingDAOImpl.getDownloadingListByBean(mContext));
        mAdapter.notifyDataSetChanged();
    }
}
