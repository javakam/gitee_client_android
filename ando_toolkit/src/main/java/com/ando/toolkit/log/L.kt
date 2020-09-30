package com.ando.toolkit.log

/**
 * Title: Log
 * <p>
 * Description:扩展函数 - 日志打印
 * </p>
 * @author javakam
 * @date 2020/9/30  10:41
 */
object L {

    //var isDebug = ToolKit.isDebug()
    fun init(globalTag: String, enable: Boolean) {
        ZLog.logGlobalTag = globalTag
        ZLog.logEnabled = enable
    }

    fun v(msg: String?, tag: String = "") = ZLog.v(msg, tag)

    fun d(msg: String?, tag: String = "") = ZLog.d(msg, tag)

    fun i(msg: String?, tag: String = "") = ZLog.i(msg, tag)

    fun w(msg: String?, tag: String = "") = ZLog.w(msg, tag)

    fun e(msg: String?, tag: String = "") = ZLog.e(msg, tag)

    fun logJson(msg: String, tag: String = "") = ZLog.json(msg, tag)

}