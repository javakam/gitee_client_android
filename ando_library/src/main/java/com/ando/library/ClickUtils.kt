package com.ando.library

import android.view.View
import com.ando.toolkit.ext.OnClickListener
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Title:ClickUtils
 *
 *
 * Description:按键防抖动
 *
 *
 * @author javakam
 * Date 2017/10/29 17:14
 */
object ClickUtils {

    fun noShake(view: View?, listener: OnClickListener?) {
        noShake(view, 1000, listener)
    }

    fun noShake(view: View?, windowDuration: Long, listener: OnClickListener?) =
        RxView.clicks(view!!)
            .throttleFirst(windowDuration, TimeUnit.MILLISECONDS)
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(o: Any) {
                    listener?.onClick()
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {}
            })


}