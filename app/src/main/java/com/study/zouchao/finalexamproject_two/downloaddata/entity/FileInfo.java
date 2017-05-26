package com.study.zouchao.finalexamproject_two.downloaddata.entity;

import com.study.zouchao.finalexamproject_two.download.DownloadStatus;

import java.io.Serializable;



/**
 * Created by Administrator on 2017/2/7.
 */

public class FileInfo implements Serializable {
    //没鸟用
    private int id;
    private String url;
    private String fileName;
    //文件总长度 （没鸟用）
    private int length;
    //已下载进度(百分比)
    private int finished;
    //（当前）已下载大小
    private long currentSize = 0L;
    //文件总大小
    private long totalSize =  0L;
    //写入DB用的 保存当前文件的状态  0..未下载 1..已暂停 2..正在下载 3..已下载
    private int currentDownloadStatus = DownloadStatus.NOT_DOWNLOAD;

    private Long totalSizeByAdd = 0L;

    //保存到本地的路径
    private String mSaveAbsoultePath;
    //
    private String mSaveDate;

    public FileInfo() {}

    public FileInfo(int id, String url, String fileName, int length, int finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public FileInfo(int id, String url, String fileName, int length, int finished, Long totalSizeByAdd) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
        this.totalSizeByAdd = totalSizeByAdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public FileInfo setFinished(int finished) {
        this.finished = finished;
        return this;
    }

    public FileInfo setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
        return this;
    }

    public FileInfo setTotalSize(long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public int getCurrentDownloadStatus() {
        return currentDownloadStatus;
    }

    public FileInfo setCurrentDownloadStatus(int currentDownloadStatus) {
        this.currentDownloadStatus = currentDownloadStatus;
        return this;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileInfo info = (FileInfo) o;

        return url != null ? url.equals(info.url) : info.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    public Long getTotalSizeByAdd() {
        return totalSizeByAdd;
    }

    public void setTotalSizeByAdd(Long totalSizeByAdd) {
        this.totalSizeByAdd = totalSizeByAdd;
    }

    public String getSaveAbsoultePath() {
        return mSaveAbsoultePath;
    }

    public void setSaveAbsoultePath(String saveAbsoultePath) {
        this.mSaveAbsoultePath = saveAbsoultePath;
    }

    public String getSaveDate() {
        return mSaveDate;
    }

    public void setSaveDate(String saveDate) {
        this.mSaveDate = saveDate;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                ", currentSize=" + currentSize +
                ", totalSize=" + totalSize +
                ", currentDownloadStatus=" + currentDownloadStatus +
                ", totalSizeByAdd=" + totalSizeByAdd +
                ", mSaveAbsoultePath='" + mSaveAbsoultePath + '\'' +
                '}';
    }
}
