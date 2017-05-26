package com.study.zouchao.finalexamproject_two.homepage.model.result;

/**
 * Created by Administrator on 2017/2/9.
 */

public class BannerItem {
    /*
     * eg：第0张
     */
    private String index;
    /*
     * eg: http://p3.zhimg.com/64/5c/645cde143c9a371005f3f749366cffad.jpg
     */
    private String url;

    public BannerItem(String index, String url) {
        this.index = index;
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerItem{" +
                "index='" + index + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
