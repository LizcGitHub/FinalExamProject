package com.study.zouchao.finalexamproject_two.util.api;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by keien on 2016/10/8.
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 */

public class MyRxManager {

    public MyRxBus mMyRxBus = MyRxBus.$();
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者

    public void on(String evenName, Action1<Object> action1){
        Observable<?> mObservable = mMyRxBus.reregister(evenName);
        mObservables.put(evenName,mObservable);
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
        .subscribe(action1,
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void add(Subscription m){
        mCompositeSubscription.add(m);
    }

    public void clear(){
        mCompositeSubscription.unsubscribe();// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mMyRxBus.unregister(entry.getKey(),entry.getValue());// 移除观察
        }
    }

    public void post(Object tag, Object content){
        mMyRxBus.post(tag,content);
    }
}
