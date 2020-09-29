package com.ando.toolkit

import android.app.Application
import android.content.Context

/**
 * Title:AppUtils
 *
 * Description:
 *
 * @author javakam
 * @date 2019/3/17 14:45
 */
object ToolKit {

    private var context: Application? = null
    private var isDebug: Boolean = false

    fun init(context: Application, isDebug: Boolean) {
        ToolKit.context = context
        ToolKit.isDebug = isDebug
    }

    fun isDebug(): Boolean = isDebug

    fun getContext(): Context = context ?: throw NullPointerException("u should init first")

    fun release() {
        context = null
    }
}