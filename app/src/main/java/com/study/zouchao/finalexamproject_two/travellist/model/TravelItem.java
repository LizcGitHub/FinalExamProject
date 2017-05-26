package com.study.zouchao.finalexamproject_two.travellist.model;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelItem {
    public String img;
    public String title;
    public String content;
    public int visitNum;
    public int imgNum;
    public String authorName;
    public String authorImg;
    public String tag;
    public String commentNum;
    public int loveNum;
    public String date;

    @Override
    public String toString() {
        return "TravelItem{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", visitNum=" + visitNum +
                ", imgNum=" + imgNum +
                ", authorName='" + authorName + '\'' +
                ", authorImg='" + authorImg + '\'' +
                ", tag='" + tag + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", loveNum=" + loveNum +
                ", date='" + date + '\'' +
                '}';
    }
}
