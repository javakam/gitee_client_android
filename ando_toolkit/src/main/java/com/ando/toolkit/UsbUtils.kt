package com.ando.toolkit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import android.text.TextUtils
import java.io.File
import java.util.*

class UsbUtils<T> private constructor() //构造函数私有化
{
    /**
     * Android 2.3之后的系统
     * 获取外部存储列表
     */
    fun getStocmtragePaths(cxt: Context): Array<String> {
        val pathsList: MutableList<String> = ArrayList()
        val storageManager = cxt.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        try {
            @SuppressLint("DiscouragedPrivateApi") val method =
                StorageManager::class.java.getDeclaredMethod("getVolumePaths")
            method.isAccessible = true
            val result = method.invoke(storageManager)
            if (result is Array<String>) {
                var statFs: StatFs
                for (path in result) {
                    if (!TextUtils.isEmpty(path) && File(path).exists()) {
                        statFs = StatFs(path)
                        if (statFs.blockCount * statFs.blockSize != 0) {
                            pathsList.add(path)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val externalFolder = Environment.getExternalStorageDirectory()
            if (externalFolder != null) {
                pathsList.add(externalFolder.absolutePath)
            }
        }
        return pathsList.toTypedArray()
    }

    companion object {
        /**
         * 单利模式  双重检测
         */
        private var usbUtils: UsbUtils<*>? = null

        // 解决指令重排序问题
        val instance: UsbUtils<*>?
            get() {
                if (usbUtils == null) {
                    synchronized(UsbUtils::class.java) {
                        var temp = usbUtils // 解决指令重排序问题
                        if (temp == null) {
                            temp = UsbUtils<Any?>()
                            usbUtils = temp
                        }
                    }
                }
                return usbUtils
            }
    }
}