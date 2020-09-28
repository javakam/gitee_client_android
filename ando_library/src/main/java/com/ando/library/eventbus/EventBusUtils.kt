package com.ando.library.eventbus

import org.greenrobot.eventbus.EventBus

object EventBusUtils {
    fun <T> register(event: T) {
        if (!EventBus.getDefault().isRegistered(event)) {
            EventBus.getDefault().register(event)
        }
    }

    fun <T> unregister(event: T) {
        if (EventBus.getDefault().isRegistered(event)) {
            EventBus.getDefault().unregister(event)
        }
    }

    fun <T> post(event: T) {
        EventBus.getDefault().post(event)
    }

    //sticky 事件 不要用同一媒介 , 会出现 sticky 事件覆盖问题
    //    @Deprecated
    //    public static <T extends EventBusMedium> void postSticky(T event) {
    //        EventBus.getDefault().postSticky(event);
    //    }
    fun <T> postSticky(event: T) {
        EventBus.getDefault().postSticky(event)
    }

    fun removeStickyEvent(event: Any?) {
        EventBus.getDefault().removeStickyEvent(event)
    }

    fun removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents()
    }
}