package com.study.zouchao.finalexamproject_two.login.model;

import android.util.Log;

import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/8.
 */

public class LoginModel implements LoginContract.ILoginModel {
    private static final String TAG = "LoginM";
    private Subscription mSubscription = null;

    @Override
    public void connXMUT(final String url, final LoginContract.ILoginModel.IConnListener listener) {
        mSubscription = rx.Observable.just(url)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            Connection.Response response =  Jsoup.connect(url)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    .execute();
                            LogLongUtil.logD("xmut", response.body());
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
                        listener.onFailure(throwable);
                        Log.d(TAG, throwable.getMessage());
                        throwable.printStackTrace();
                        //TODO:递归解决
                        connXMUT(url, listener);
                    }
                });
    }

    /**
     * 得到cookies
     * @param url
     * @param listener
     */
    @Override
    public void connAndgetCookies(final String url, final IConnListener listener) {
        mSubscription = rx.Observable.just(url)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            Connection.Response response =  Jsoup.connect(url)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    .execute();
                            LogLongUtil.logD("body", response.body());
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
                        listener.onFailure(throwable);
                        Log.d(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * 请求验证码（需要带有cookies）
     * @param url
     * @param cookies
     * @param listener
     */
    @Override
    public void getCheckCodeBytes(String url, final Map<String, String> cookies, final IConnListener listener) {
        mSubscription = rx.Observable.just(url)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            return Jsoup.connect(C_XMUT.CHECK_CODE_URL)
                                    .cookies(cookies)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    .ignoreContentType(true)
                                    .execute();
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
                        listener.onFailure(throwable);
                        Log.d(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * 登陆 (需要cookies)
     * @param params
     * @param cookies
     */
    @Override
    public void login(String uri, final Map<String, String> params, final Map<String, String> cookies, final IConnListener listener) {
        mSubscription = rx.Observable.just(uri)
                .map(new Func1<String, Connection.Response>() {
                    @Override
                    public Connection.Response call(String url) {
                        try {
                            final Connection.Response response =  Jsoup.connect(url)
                                    .data(params)
                                    .timeout(C_XMUT.TIME_OUT_SECONDS)
                                    .cookies(cookies)
                                    .execute();
                            LogLongUtil.logD("登陆后", response.body());
                            LogLongUtil.logD("访问的网址..", response.url().toString());
                            LogLongUtil.logD("cookies..", "key.."+response.cookies().keySet().toString());
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
                        listener.onFailure(throwable);
                    }
                });
    }

    @Override
    public void onDestroyModel() {
        if (mSubscription != null || mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            Log.d(TAG, "mSubscription..onDestroy..取消绑定");
        } else {
            Log.d(TAG, " mSubscription.isUnsubscribed || null");
        }
    }
}
