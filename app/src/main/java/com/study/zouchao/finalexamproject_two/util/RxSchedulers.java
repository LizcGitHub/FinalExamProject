package com.study.zouchao.finalexamproject_two.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by keien on 2016/10/11.
 */

public class RxSchedulers {
    public static <T>Observable.Transformer<T,T> io_main(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
        //return tObservable -> tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 只为Sleep
     * @param sleepTimeMillis sleep的秒数
     * @param <T>
     * @return
     */
    public static <T> Func1<T, T> sleep(final long sleepTimeMillis) {
        return new Func1<T, T>() {
            @Override
            public T call(T t) {
                try {
                    Thread.sleep(sleepTimeMillis);
                } catch (InterruptedException e) {e.printStackTrace();}
                return t;
            }
        };
    }
}
