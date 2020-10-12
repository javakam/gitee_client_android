package com.ando.library.utils

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Title: View 按键防抖动
 *
 * @author changbao
 * Date 2017/10/29 17:14
 */

inline fun <T : View> T.noShake(crossinline listener: T.() -> Unit) = this.noShake(1000, listener)

inline fun <T : View> T.noShake(windowDuration: Long = 500, crossinline listener : T.() -> Unit) =
    RxView.clicks(this)
        .throttleFirst(windowDuration, TimeUnit.MILLISECONDS)
        .subscribe(object : Observer<Any> {
            override fun onSubscribe(d: Disposable) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
            override fun onNext(o: Any) {
                listener()
            }
        })