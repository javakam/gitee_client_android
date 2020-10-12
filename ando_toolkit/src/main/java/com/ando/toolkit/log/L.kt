package com.ando.toolkit.log

/**
 * Title: Log
 * <p>
 * Description:扩展函数 - 日志打印
 * </p>
 * @author changbao
 * @date 2020/9/30  10:41
 */
object L {

    //var isDebug = ToolKit.isDebug()
    fun init(globalTag: String, enable: Boolean) {
        ZLog.logGlobalTag = globalTag
        ZLog.logEnabled = enable
    }

    fun v(tag: String = "", msg: String?) = ZLog.v(msg, tag)

    fun d(tag: String = "", msg: String?) = ZLog.d(msg, tag)

    fun i(tag: String = "", msg: String?) = ZLog.i(msg, tag)

    fun w(tag: String = "", msg: String?) = ZLog.w(msg, tag)

    fun e(tag: String = "", msg: String?) = ZLog.e(msg, tag)

    fun logJson(tag: String = "", msg: String) = ZLog.json(msg, tag)

}