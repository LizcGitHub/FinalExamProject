package com.study.zouchao.finalexamproject_two.schoolpictures.model;

import android.util.Log;

import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2017/5/17.
 */

public class SchoolPicsModel {
    private IGetShoolPicsListener mListener;
    private List<String> mSeeMoreImgs;

    /**
     * 连接网络 加载图片
     * @param listener
     */
    public void connPics(IGetShoolPicsListener listener) {
        mListener = listener;
        mSeeMoreImgs = new ArrayList<>();
        start();
    }
    private void start() {
        try {
            conn();
        } catch (IOException e) {
            e.printStackTrace();
            onFailureCallback(e, "连接失败");
        }
    }

    private void conn() throws IOException {
        Observable.just("https://www.xmut.edu.cn/xxgk/xxjj/")
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
                .map(RxSchedulers.<Document>sleep(3000))

                .compose(RxSchedulers.<Document>io_main())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document doc) {
//                        LogLongUtil.logI("更多图片html", doc.toString());
                        parseToSeeMorePic(doc);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onFailureCallback(throwable, "得到图片失败!!");
                    }
                });

    }


    private void parseToSeeMorePic(Document doc) {
        if (doc == null) {
            onFailureCallback(null, "doc为空");
            return;
        }
        Elements elesSlideItems = doc.select(".sidebar-img.gallery");
        for (Element eleSlideItem : elesSlideItems) {
            Elements elesImgs = eleSlideItem.getElementsByTag("img");
            for (Element eleImg : elesImgs) {
                if (eleImg != null) {
                    String imgUrl = eleImg.attr("src");
                    Log.d(TAG, "图片路径" + imgUrl);
                    addAbsoulteImgUrl2List(imgUrl);
                } else {
                    Log.i("解析更多图片", "某个img元素不存在");
                }
            }
        }
        printList("得到所有更多图片", mSeeMoreImgs);
        //保存
        saveData2SharePrefer(mSeeMoreImgs);
        //加载数据到界面
        onSuccessCallback(mSeeMoreImgs);
    }


    private void saveData2SharePrefer(List<String> data) {
//        SchoolPicsCacheUtil.saveSchoolPics(m);
    }

    private void addAbsoulteImgUrl2List(String url) {
        if (StringUtils.isEmpty(url)) return;
        String absoulteUrl = url.replace("../", "https://www.xmut.edu.cn/xxgk/");
        Log.i("绝对路径", absoulteUrl);
        if (StringUtils.isEmpty(absoulteUrl)) return;
        mSeeMoreImgs.add(absoulteUrl);
    }

    private void printList(String tag, List<String> list) {
        for (String ele : list) {
            LogLongUtil.logI(tag, ele);
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


    public interface IGetShoolPicsListener {
        void onSuccess(List<String> data);
        void onFailure(Throwable throwable, String msg);
    }
}
