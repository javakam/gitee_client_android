package com.gitee.android.http.rx

import io.reactivex.subscribers.ResourceSubscriber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 *  自定义 ResourceSubscriber
 */
abstract class RxSubscriber<T> : ResourceSubscriber<T>() {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(throwable: Throwable) {
        //网络异常
        val message = throwable.message ?: ""
        if (throwable is ConnectException || throwable is SocketTimeoutException || throwable is TimeoutException) {
            //onNetWorkConnectError(throwable.getMessage());
            onFail(message)
        } else if (throwable is UnknownHostException) {
            onFail(message)
        } else {
            onFail(message)
        }
    }

    override fun onComplete() {
        onCompleted()
    }

    abstract fun onSuccess(response: T?) //onNext()

    open fun onNetWorkConnectError(message: String?) {}

    /**
     * 其他错误（非网络）
     *
     * @param message
     */
    abstract fun onFail(message: String)

    //onComplete()
    fun onCompleted() {}

}