package com.study.zouchao.finalexamproject_two.downloaddata.downloading.model.dto;

/**
 * Created by Administrator on 2017/2/12.
 */

public class DownloadTaskDTO {
    public String url;
    //要传递的消息
    public String msg;
    public DownloadTaskDTO(String url, String msg) {
        this.url = url;
        this.msg = msg;
    }
}
