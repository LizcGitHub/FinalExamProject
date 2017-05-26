package com.study.zouchao.finalexamproject_two.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/12/1.
 */

public class EventBusUtils {
    /**
     * register
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if ( ! EventBus.getDefault().isRegistered(subscriber) )
            EventBus.getDefault().register(subscriber);
    }

    /**
     * unregister
     * @param subscriber
     */
    public static void unRegister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * post
     * @param event
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    public static void postStickyEvent(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 移除指定粘性事件
     * @param stickyEvent eg: String.class
     *     因为map的key是形参类的字节码
     */
    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null)
            EventBus.getDefault().removeStickyEvent(stickyEvent);
    }

    /**
     * 移除所有粘性事件
     */
    public static void removeAllStickyEvent() {
        EventBus.getDefault().removeAllStickyEvents();
    }
}
