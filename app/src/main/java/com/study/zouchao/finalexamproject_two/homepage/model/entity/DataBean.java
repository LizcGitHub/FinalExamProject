package com.study.zouchao.finalexamproject_two.homepage.model.entity;

/**
 * Created by Administrator on 2017/5/17.
 */

public class DataBean {
    public String department;
    public String title;
    public String author;
    public String dataDownloadUrl;
    public long   totalSize;

    @Override
    public String toString() {
        return "DataBean{" +
                "department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dataDownloadUrl='" + dataDownloadUrl + '\'' +
                ", totalSize=" + totalSize +
                '}';
    }
}
