package com.ando.library.views

import android.view.View
import com.ando.toolkit.log.L

/**
 * Title: AbstractDoubleClickListener
 *
 * Description: 双击监听 & 双击防抖
 *
 * @author javakam
 * @date 2020-04-15
 */
abstract class AbstractDoubleClickListener constructor(
    private val doubleRespDuration: Long = DOUBLE_RESPONSE_TIME,
    private val shakeDuration: Long = SHAKE_DURATION
) : View.OnClickListener {
    
    private var lastClickTime: Long = 0
    
    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()
        val diffTimeMillis = currentTimeMillis - lastClickTime
        if (diffTimeMillis < doubleRespDuration) {
            L.w("time", "time 222")
            onDoubleClick(v)
        }
        lastClickTime = currentTimeMillis

//      > shakeDuration 防抖: 需要大于这个时间差才可以再次响应时间
//      if (diffTimeMillis > shakeDuration) {
//          L.w("time","time 1111");
//      }
    }

    abstract fun onDoubleClick(v: View?)

    companion object {
        //默认双击事件响应间隔 300 毫秒
        private const val DOUBLE_RESPONSE_TIME: Long = 300

        //双击防抖 1500 毫秒
        private const val SHAKE_DURATION: Long = 1500
    }
}