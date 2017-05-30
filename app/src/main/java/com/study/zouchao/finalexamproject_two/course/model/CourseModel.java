package com.study.zouchao.finalexamproject_two.course.model;

import android.util.Log;


import com.study.zouchao.finalexamproject_two.course.contract.CourseContract;
import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.util.LogUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/9.
 */

public class CourseModel implements CourseContract
        .ICourseModel {
    private static final String TAG = "CourseModel";
    @Override
    public void connCoursePage(String url, final Map<String, String> cookies, final LoginContract.ILoginModel.IConnListener listener) throws UnsupportedEncodingException {
        LogUtils.d(TAG, "url..\n" + url);
        rx.Observable.just(url)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            Connection.Response response =  Jsoup.connect(url)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    /**
                                     * 注意：必须加上Referrer
                                     *      因为查课表时:地址栏url并没有发生改变
                                     *          说明是一个请求转发
                                     * 否则会出现异常：
                                     *      java.net.MalformedURLException
                                     */
                                    .referrer(C_XMUT.REFER_URL)
                                    .cookies(cookies)
                                    .execute();
                            return response;
                        } catch (IOException e) {e.printStackTrace();return null;}
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connection.Response>() {
                    @Override
                    public void call(Connection.Response response) {
                        listener.onSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage() );
                        throwable.printStackTrace();
                        listener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void connTest(ITestListener listener) {

    }

}
