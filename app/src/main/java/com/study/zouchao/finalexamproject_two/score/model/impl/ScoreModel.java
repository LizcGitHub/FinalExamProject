package com.study.zouchao.finalexamproject_two.score.model.impl;

import android.util.Log;


import com.study.zouchao.finalexamproject_two.login.loginStatus.LoginStatus;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.score.contract.IScoreContract;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.LogUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 查询成绩ModelImpl
 */

public class ScoreModel implements IScoreContract.IScoreModel {
    private Subscription mSubscription;
    /**
     * 加载当前学年的成绩
     * @param url
     * @param params
     * @param listener
     */
    @Override
    public void connScoreCurrentAdademicYear(String url, final Map<String, String> params, final IScoreContract.IConnListener listener) {
        //使用RxJava 开启一个线程 加载网络数据
        mSubscription = Observable.just(url)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            Log.i("查询成绩连接的url", url);
                            Log.i("查询成绩连接的ref", C_XMUT.REFER_URL);
                            LogUtils.i("查询成绩连接的cookies", LoginStatus.cookies.toString());
                            return Jsoup.connect(url)
                                    .cookies(LoginStatus.cookies)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    /**
                                     * 查询当前学年成绩：
                                     *      refer：Refer_URL(进入后的主页)
                                     */
                                    .referrer(C_XMUT.REFER_URL)
//                                    .data(params)
                                    .execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("没事抛个异常玩玩");
                            //抛出一个异常
//                            ErrorUtils.createException();
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connection.Response>() {
                    @Override
                    public void call(Connection.Response response) {
                        if (response != null && response.body() != null) {
                            try {
                                listener.onSuccess(response);
                            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
    }

    /**
     * 加载其他学年的成绩
     * @param url
     * @param params
     * @param listener
     */
    @Override
    public void connScoreOtherAdademicYear(String url, final Map<String, String> params, final IScoreContract.IConnReturnDocListener listener) {
        mSubscription = Observable.just(url)
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String url) {
                        try {
                            return Jsoup.connect(url)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    .cookies(LoginStatus.cookies)
                                    /**
                                     * Refer为Score页面
                                     */
                                    .referrer(C_XMUT.SCORE_URL)
                                    /**
                                     * 查询其他学年是有参数的post请求
                                     */
                                    .data(params)
                                    /**
                                     * post请求
                                     */
                                    .post();
                        } catch (IOException e) {
                            e.printStackTrace();
                            //抛出一个异常
                            throw new RuntimeException("没事抛个异常玩玩");
                        }
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document document) {
                        if (document != null)
                            listener.onSuccess(document);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                });
    }

}
