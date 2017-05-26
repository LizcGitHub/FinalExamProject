package com.study.zouchao.finalexamproject_two.homepage.model.impl;

import android.util.Log;

import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2017/5/17.
 */

public class AdConnModel {
    private List<String> mSlideImgs;
    private IGetAdListener  mListener;
    public void connAd(IGetAdListener listener) {
        mSlideImgs = new ArrayList<>();
        mListener = listener;
        start();
    }

    private void start() {
        try {
            getAdPic();
        } catch (IOException e) {
            e.printStackTrace();
            onFailureCallback(e, "连接失败");
        }
    }

    private void getAdPic() throws IOException {
        Observable.just("https://www.xmut.edu.cn/")
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String url) {
                        try {
                            return Jsoup.connect(url)
                                    .timeout(C.CONN_TIME_OUT)
                                    .get();
                        } catch (IOException e) {e.printStackTrace();return null;}
                    }
                })
//                .map(RxSchedulers.<Document>sleep(2000))
                .compose(RxSchedulers.<Document>io_main())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document doc) {
//                        LogLongUtil.logI("理工首页html", doc.toString());
                        parseToAdPic(doc);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onFailureCallback(throwable, "得到首页失败!!");
                    }
                });
    }



    private void parseToAdPic(Document doc) {
        if (doc == null) {
            onFailureCallback(null, "没有doc");
            return;
        }
        Elements elesSlideItems = doc.getElementsByClass("slide-item");
        for (Element eleSlideItem : elesSlideItems) {
            Element eleImg = eleSlideItem.getElementsByTag("img").get(0);
            if (eleImg != null) {
                String imgUrl = eleImg.attr("src");
                Log.d(TAG, "图片路径" + imgUrl);
                addAbsoulteImgUrl2List(imgUrl);
            } else {
                Log.i("解析轮播图", "不存在img元素");
            }
        }
        printList("得到所有完整的轮播图片", mSlideImgs);
        onSuccessCallback(mSlideImgs);
    }

    private void addAbsoulteImgUrl2List(String url) {
        if (StringUtils.isEmpty(url)) return;
        String absoulteUrl = url.replace("./", "https://www.xmut.edu.cn/");
        Log.i("绝对路径", absoulteUrl);
        if (StringUtils.isEmpty(absoulteUrl)) return;
        mSlideImgs.add(absoulteUrl);
    }

    private void printList(String tag, List<String> list) {
        for (String ele : list) {
//            LogLongUtil.logI(tag, ele);
        }
    }


    private void onFailureCallback(Throwable throwable, String msg) {
        if (mListener != null) {
            mListener.onFailure(throwable, msg);
        }
    }

    private void onSuccessCallback(List<String> data) {
        if (mListener != null) {
            mListener.onSuccess(data);
        }
    }


    public interface IGetAdListener {
        void onSuccess(List<String> mSlideImgs);
        void onFailure(Throwable throwable, String msg);
    }
}
