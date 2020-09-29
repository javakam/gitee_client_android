package com.ando.toolkit.ext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Title: 扩展函数 - APP信息
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/9/29  16:56
 */

/* ---------- Context ---------- */

/**
 * 获取版本号  123
 */
val Context.versionCode: Long
    get() = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        if (packageInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        } else 1L
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        1L
    }

/**
 * 获取版本名 1.0.0
 */
val Context.versionName: String
    get() = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }

/**
 * 获取app名称
 */
val Context.appName: String
    get() = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        resources.getString( packageInfo.applicationInfo.labelRes)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }


