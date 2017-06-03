package com.study.zouchao.finalexamproject_two.searchweather;

import android.util.Log;

import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.searchweather.entity.WeatherEntity;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/3.
 */

public class SearchWeatherModel {
    private static final String URL = "http://guolin.tech/api/weather?cityid=CN101230201&key=6386e297bac249c5a267460f1b2c7da0";
    public interface IGetWeatherListener {
        void onSuccess(WeatherEntity weatherEntity);
        void onFailure(Throwable throwable, String msg);
    }
    private IGetWeatherListener mListener;
    public Call conn() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL)
                .build();
        Call call = okHttpClient.newCall(request);
        return call;

    }
}
