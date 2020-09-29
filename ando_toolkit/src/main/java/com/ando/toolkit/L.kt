package com.ando.toolkit

import android.util.Log

/**
 * Log 统一管理类
 *
 * @author javakam
 * @date 2018-7-9
 */
object L {
    private const val TAG = "123"
    var isDebug = ToolKit.isDebug // 是否需要打印bug，可以在application的onCreate函数里面初始化

    // 下面四个是默认tag的函数
    @JvmStatic
    fun i(msg: String?) {
        if (isDebug) {
            Log.i(TAG, msg!!)
        }
    }

    @JvmStatic
    fun d(msg: String?) {
        if (isDebug) {
            Log.d(TAG, msg!!)
        }
    }

    @JvmStatic
    fun e(msg: String?) {
        if (isDebug) {
            Log.e(TAG, msg!!)
        }
    }

    @JvmStatic
    fun w(msg: String?) {
        if (isDebug) {
            Log.w(TAG, msg!!)
        }
    }

    @JvmStatic
    fun v(msg: String?) {
        if (isDebug) {
            Log.v(TAG, msg!!)
        }
    }

    @JvmStatic
    fun wtf(msg: String?) {
        if (isDebug) {
            Log.wtf(TAG, msg)
        }
    }

    // 下面是传入自定义tag的函数
    @JvmStatic
    fun i(tag: String?, msg: String?) {
        if (isDebug) {
            Log.i(tag, msg!!)
        }
    }

    @JvmStatic
    fun d(tag: String?, msg: String?) {
        if (isDebug) {
            Log.d(tag, msg!!)
        }
    }

    @JvmStatic
    fun e(tag: String?, msg: String?) {
        if (isDebug) {
            Log.e(tag, msg!!)
        }
    }

    @JvmStatic
    fun w(tag: String?, msg: String?) {
        if (isDebug) {
            Log.w(tag, msg!!)
        }
    }

    @JvmStatic
    fun v(tag: String?, msg: String?) {
        if (isDebug) {
            Log.v(tag, msg!!)
        }
    }

    @JvmStatic
    fun wtf(tag: String?, msg: String?) {
        if (isDebug) {
            Log.wtf(tag, msg)
        }
    }

    /**
     * 截断输出日志
     *
     * @param msg
     */
    @JvmStatic
    fun ee(msg: String?) {
        var msg = msg
        if (isDebug) {
            if (msg == null || msg.isEmpty()) {
                return
            }
            val segmentSize = 5 * 1024
            val length = msg.length.toLong()
            // 打印剩余日志
            if (length > segmentSize) { // 长度小于等于限制直接打印
                while (msg!!.length > segmentSize) { // 循环分段打印日志
                    val logContent = msg.substring(0, segmentSize)
                    msg = msg.replace(logContent, "")
                    Log.e(TAG, logContent)
                }
            }
            Log.e(TAG, msg)
        }
    }

    @JvmStatic
    fun ww(msg: String?) {
        var msg = msg
        if (isDebug) {
            if (msg == null || msg.isEmpty()) {
                return
            }
            val segmentSize = 5 * 1024
            val length = msg.length.toLong()
            // 打印剩余日志
            if (length > segmentSize) { // 长度小于等于限制直接打印
                while (msg!!.length > segmentSize) { // 循环分段打印日志
                    val logContent = msg.substring(0, segmentSize)
                    msg = msg.replace(logContent, "")
                    Log.w(TAG, logContent)
                }
            }
            Log.w(TAG, msg)
        }
    }

    @JvmStatic
    fun dd(msg: String?) {
        var msg = msg
        if (isDebug) {
            if (msg == null || msg.isEmpty()) return
            val segmentSize = 5 * 1024
            val length = msg.length.toLong()
            // 打印剩余日志
            if (length > segmentSize) { // 长度小于等于限制直接打印
                while (msg!!.length > segmentSize) { // 循环分段打印日志
                    val logContent = msg.substring(0, segmentSize)
                    msg = msg.replace(logContent, "")
                    Log.d(TAG, logContent)
                }
            }
            Log.d(TAG, msg)
        }
    }

}