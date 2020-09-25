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

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    fun init(context: Application, isDebug: Boolean) {
        AppUtils.context = context
        AppUtils.isDebug = isDebug
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    @JvmStatic
    fun getContext(): Context = context ?: throw NullPointerException("u should init first")

    fun release() {
        context = null
    }
}