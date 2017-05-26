package com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto;


/**
 * 用于正在下载 时更新UI 下载进度
 * Created by Administrator on 2017/2/12.
 */

public class UpdateProgressDTO {
    //文件的url
    public String url;
    //当前下载进度 progress%
    public int progress;
    //文件总大小 单位M
    public long total;
    //文件当前下载大小 单位M
    public long current;

    public UpdateProgressDTO(String url, int progress, long total, long current) {
        this.url = url;
        this.progress = progress;
        this.total = total;
        this.current = current;
    }

    public UpdateProgressDTO(String url, int progress) {
        this.progress = progress;
        this.url = url;
    }

}
