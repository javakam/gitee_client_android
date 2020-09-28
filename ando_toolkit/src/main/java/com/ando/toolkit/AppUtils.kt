package com.ando.toolkit

import android.app.Application
import android.content.Context

/**
 * Title:AppUtils
 *
 *
 * Description:
 *
 *
 * @author javakam
 * @date 2019/3/17 14:45
 */
object AppUtils {

    private var context: Application? = null
    @JvmStatic
    var isDebug = false
        private set

    fun init(context: Application, isDebug: Boolean) {
        AppUtils.context = context
        AppUtils.isDebug = isDebug
    }

    /**
     * 获取 ApplicationContext
     */
    @JvmStatic
    fun getContext(): Context = context ?: throw NullPointerException("u should init first")

    fun release() {
        context = null
    }
}