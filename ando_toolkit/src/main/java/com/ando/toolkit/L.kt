package com.ando.toolkit

import android.util.Log

/**
 * Log 统一管理类
 *
 * @author javakam
 * @date 2018-7-9
 */
class L private constructor() {
    companion object {
        private const val TAG = "123"
        var isDebug = AppUtils.isDebug // 是否需要打印bug，可以在application的onCreate函数里面初始化

        // 下面四个是默认tag的函数
        fun i(msg: String?) {
            if (isDebug) {
                Log.i(TAG, msg!!)
            }
        }

        fun d(msg: String?) {
            if (isDebug) {
                Log.d(TAG, msg!!)
            }
        }

        fun e(msg: String?) {
            if (isDebug) {
                Log.e(TAG, msg!!)
            }
        }

        fun w(msg: String?) {
            if (isDebug) {
                Log.w(TAG, msg!!)
            }
        }

        fun v(msg: String?) {
            if (isDebug) {
                Log.v(TAG, msg!!)
            }
        }

        fun wtf(msg: String?) {
            if (isDebug) {
                Log.wtf(TAG, msg)
            }
        }

        // 下面是传入自定义tag的函数
        fun i(tag: String?, msg: String?) {
            if (isDebug) {
                Log.i(tag, msg!!)
            }
        }

        fun d(tag: String?, msg: String?) {
            if (isDebug) {
                Log.d(tag, msg!!)
            }
        }

        fun e(tag: String?, msg: String?) {
            if (isDebug) {
                Log.e(tag, msg!!)
            }
        }

        fun w(tag: String?, msg: String?) {
            if (isDebug) {
                Log.w(tag, msg!!)
            }
        }

        fun v(tag: String?, msg: String?) {
            if (isDebug) {
                Log.v(tag, msg!!)
            }
        }

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
        fun ee(msg: String?) {
            var msg = msg
            if (isDebug) {
                if (msg == null || msg.length == 0) {
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

        fun ww(msg: String?) {
            var msg = msg
            if (isDebug) {
                if (msg == null || msg.length == 0) {
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

        fun dd(msg: String?) {
            var msg = msg
            if (isDebug) {
                if (msg == null || msg.length == 0) {
                    return
                }
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

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}