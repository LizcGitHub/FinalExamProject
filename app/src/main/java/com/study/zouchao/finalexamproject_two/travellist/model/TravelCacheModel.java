package com.study.zouchao.finalexamproject_two.travellist.model;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.base_zou.cache.TravelCacheUtil;
import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TravelCacheModel {
    public void saveTravelData2SharePre(Context context, List<TravelListResult.ResultBean> newData) {
        TravelCacheUtil.saveTravelData(context, newData);
    }

    public List<TravelListResult.ResultBean> listTravelData(Context context) {
        return TravelCacheUtil.listTravelCacheByList(context);
    }
}
