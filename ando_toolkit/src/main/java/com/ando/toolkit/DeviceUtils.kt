package com.ando.toolkit

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Device 工具类
 *
 * <pre>
 * DeviceUtils.getAppName(this)        证券日报
 * DeviceUtils.getVersionName(this)    1.0.6
 * DeviceUtils.getVersionCode(this)    100
 * </pre>
 *
 * @author javakam
 * @date 2020-02-27
 */
object DeviceUtils {

    /**
     * 获取手机相对分辨率
     *
     *
     * https://www.jianshu.com/p/1a931d943fb4
     */
    fun getScreenRelatedInformation(context: Context): String {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels
        val heightPixels = outMetrics.heightPixels
        val densityDpi = outMetrics.densityDpi
        val density = outMetrics.density
        val scaledDensity = outMetrics.scaledDensity
        //可用显示大小的绝对宽度（以像素为单位）。
        //屏幕密度表示为每英寸点数。
        //显示器的逻辑密度。
        //显示屏上显示的字体缩放系数。
        L.d(
            """
                widthPixels = $widthPixels,heightPixels = $heightPixels
                ,densityDpi = $densityDpi
                ,density = $density,scaledDensity = $scaledDensity
            """.trimIndent()
        )
        return widthPixels.toString() + "x" + heightPixels
    }

    /**
     * 获取手机绝对分辨率
     *
     *
     * https://www.jianshu.com/p/1a931d943fb4
     */
    fun getRealScreenRelatedInformation(context: Context): String {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels
        val heightPixels = outMetrics.heightPixels
        val densityDpi = outMetrics.densityDpi
        val density = outMetrics.density
        val scaledDensity = outMetrics.scaledDensity
        //可用显示大小的绝对宽度（以像素为单位）。
        //可用显示大小的绝对高度（以像素为单位）。
        //屏幕密度表示为每英寸点数。
        //显示器的逻辑密度。
        //显示屏上显示的字体缩放系数。
        L.d(
            """
                widthPixels = $widthPixels,heightPixels = $heightPixels
                ,densityDpi = $densityDpi
                ,density = $density,scaledDensity = $scaledDensity
            """.trimIndent()
        )
        return widthPixels.toString() + "x" + heightPixels
    }

    /**
     * 获取当前手机系统版本号
     */
    fun getSystemVersion(): String? = Build.VERSION.RELEASE

    /**
     * 获取手机型号
     */
    fun getSystemModel(): String? = Build.MODEL

    /**
     * 获取手机厂商
     */
    fun getDeviceBrand(): String? = Build.BRAND

    /**
     * 获取手机设备名
     */
    fun getSystemDevice(): String? = Build.DEVICE

    /**
     * 获取 CPU ABI
     */
    fun getCpuABIS(): Array<String?>? = Build.SUPPORTED_ABIS
}