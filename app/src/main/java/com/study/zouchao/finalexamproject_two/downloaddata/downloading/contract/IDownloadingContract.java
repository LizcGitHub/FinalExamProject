package com.study.zouchao.finalexamproject_two.downloaddata.downloading.contract;

import android.widget.BaseAdapter;

import com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto.UpdateProgressDTO;


/**
 * Created by Administrator on 2017/2/12.
 */

public interface IDownloadingContract {
    public interface IDownloadingModel {

    }

    public interface IDownloadingPresenter {
        //更新正在下载进度条
        void updateProgress(UpdateProgressDTO dto);
        void updateDownloadingListViewItemCount();
    }

    public interface IDownloadingView {
        void setListViewAdapter(BaseAdapter adapter);
    }
}
