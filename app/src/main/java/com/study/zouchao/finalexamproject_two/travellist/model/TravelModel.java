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
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2017/5/24.
 */

public class TravelModel {
    public static final String URL = "http://you.ctrip.com/travels/xiamen21.html";
    public static final String URL2 = "https://m.ctrip.com/html5/you/travels/xiamen21.html";

    private IGetShoolPicsListener mListener;
    private List<String> mSeeMoreImgs;

    private List<TravelItem> mItems;
    Map<String, String> mParams;

    /**
     * 连接网络 加载图片
     * @param listener
     */
    public void connPics(Map<String, String> params, IGetShoolPicsListener listener) {
        mParams = params;
        mListener = listener;
        mSeeMoreImgs = new ArrayList<>();
        mItems = new ArrayList<>();

    }


    public Observable<TravelListResult> listTravelList(Map<String, String> params) {
        return Api.getInstance().mApiService
                .listTravelList(params)
                .compose(RxSchedulers.<TravelListResult>io_main());
    }

    private void parseToSeeMorePic(Document doc) {
        if (doc == null) {
            onFailureCallback(null, "doc为空");
            return;
        }

        Elements elesList = doc.select("#travels_list");
        if (elesList.size() <= 0)   return;
        Element eleList = elesList.get(0);
        Elements elesItems = eleList.select("#c_travels");
        if (elesItems.size() <= 0)   return;
        for (Element eleItem : elesItems) {
            TravelItem item = new TravelItem();

            LogLongUtil.logI("travel item", eleItem.html());

            Elements elesImg = eleItem.select("img");
            if (elesImg.size() > 0) {
                Element eleImg = elesImg.get(0);
                item.img = eleImg.attr("src");
            }

            Elements elesTitle = eleItem.select("h3[class=hd]");
            if (elesTitle.size() > 0) {
                Element eleTitle = elesTitle.get(0);
                item.title = eleTitle.html();
            }

            Elements elesAuthorImg = eleItem.select("img[class=portrait]");
            if (elesAuthorImg.size() > 0) {
                Element eleAuthorImg = elesAuthorImg.get(0);
                item.authorImg = eleAuthorImg.attr("src");
            }

            Elements elesAuthorName = eleItem.select("span[class=author]");
            if (elesAuthorName.size() > 0) {
                Element eleAuthorName = elesAuthorName.get(0);
                item.authorName = eleAuthorName.html();
            }

            Elements elesDate = eleItem.select("p[class=time]");
            if (elesDate.size() > 0) {
                Element eleDate = elesDate.get(0);
                item.date = eleDate.html();
            }



            mItems.add(item);

        }
        printList("得到所有更多图片", mSeeMoreImgs);
        printList("携程", mItems, "");
        //保存
//        saveData2SharePrefer(mSeeMoreImgs);
        //加载数据到界面
//        onSuccessCallback(mSeeMoreImgs);
        onSuccessCallback(mItems);
    }



    private void printList(String tag, List<String> list) {
        for (String ele : list) {
            LogLongUtil.logI(tag, ele);
        }
    }

    private void printList(String tag, List<TravelItem> list, String t) {
        for (TravelItem ele : list) {
            LogLongUtil.logI(tag, ele.toString());
        }
    }



    private void onFailureCallback(Throwable throwable, String msg) {
        if (mListener != null) {
            mListener.onFailure(throwable, msg);
        }
    }

//    private void onSuccessCallback(List<String> data) {
//        if (mListener != null) {
//            mListener.onSuccess(data);
//        }
//    }


    private void onSuccessCallback(List<TravelItem> data) {
        if (mListener != null) {
            mListener.onSuccess(data);
        }
    }


    public interface IGetShoolPicsListener {
//        void onSuccess(List<String> data);
        void onSuccess(List<TravelItem> data);
        void onFailure(Throwable throwable, String msg);
    }


}
