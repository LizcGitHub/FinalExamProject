package com.study.zouchao.finalexamproject_two.homepage.model.result;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class DataBean {
    private List<BannerItem> bannerItemList;

    public DataBean(List<BannerItem> bannerItemList) {
        this.bannerItemList = bannerItemList;
    }

    public DataBean() {
    }

    public List<BannerItem> getBannerItemList() {
        return bannerItemList;
    }

    public void setBannerItemList(List<BannerItem> bannerItemList) {
        this.bannerItemList = bannerItemList;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "bannerItemList=" + bannerItemList +
                '}';
    }
}
