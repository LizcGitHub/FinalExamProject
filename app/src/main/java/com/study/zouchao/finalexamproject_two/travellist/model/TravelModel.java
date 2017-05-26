package com.study.zouchao.finalexamproject_two.travellist.model;

import android.util.Log;


import com.study.zouchao.finalexamproject_two.travellist.entity.TravelListResult;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.api.api.Api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelModel {
    public static final String URL = "http://you.ctrip.com/travels/xiamen21.html";
    public static final String URL2 = "https://m.ctrip.com/html5/you/travels/xiamen21.html";

    public Observable<TravelListResult> listTravelList(Map<String, String> params) {
        return Api.getInstance().mApiService
                .listTravelList(params)
//                .map(RxSchedulers.<TravelListResult>sleep(3000))
                .compose(RxSchedulers.<TravelListResult>io_main());
    }

}
