package com.ando.toolkit

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.NetworkInterface
import java.util.*

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
     * 获取版本号  123
     */
    fun getVersionCode(context: Context): Long {
        val versionCode = 1
        try {
            // 获取包管理工具类
            val pm = context.packageManager
            // 获取版本信息
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            if (packageInfo != null) {
                val appVersionCode: Long
                appVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    packageInfo.versionCode.toLong()
                }
                return appVersionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode.toLong()
    }

    /**
     * 获取版本名 1.0.0
     */
    fun getVersionName(context: Context): String {
        //获取包管理器
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //返回版本号
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取App的名称
     */
    fun getAppName(context: Context): String {
        val pm = context.packageManager
        //获取包信息
        try {
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            //获取应用 信息
            val applicationInfo = packageInfo.applicationInfo
            //获取 labelRes
            val labelRes = applicationInfo.labelRes
            //返回App的名称
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取 WIFI 信号强度 RSSI
     */
    @SuppressLint("MissingPermission")
    fun getWifiRSSI(context: Context): Int {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.connectionInfo.rssi
    }

    /**
     * 获取 WIFI 的 SSID
     */
    fun getWifiSSID(context: Context): String {
        val ssid = "unknown id"
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            val mWifiManager =
                (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
            @SuppressLint("MissingPermission") val info = mWifiManager.connectionInfo
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                info.ssid
            } else {
                info.ssid.replace("\"", "")
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            val connManager =
                (context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            @SuppressLint("MissingPermission") val networkInfo = connManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                if (networkInfo.extraInfo != null) {
                    return networkInfo.extraInfo.replace("\"", "")
                }
            }
        }
        return ssid
    }

    fun isWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val info = connectivityManager.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_WIFI
    }
    /////////////////////////////////获取MAC地址//////////////////////////////////
    /**
     * 获取mac地址（适配所有Android版本）
     */
    fun getMac(context: Context?): String? {
        var mac: String? = ""
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware()
        }
        return mac
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context * @return
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getMacDefault(context: Context?): String? {
        var mac = ""
        if (context == null) {
            return mac
        }
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var info: WifiInfo? = null
        try {
            info = wifi.connectionInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (info == null) {
            return null
        }
        mac = info.macAddress
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH)
        }
        return mac
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    fun getMacAddress(): String? {
        var macSerial: String? = null
        var str = ""
        try {
            val pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)
            while (str.isBlank()) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' } //去空格
                    break
                }
            }
        } catch (ex: IOException) {
            // 赋予默认值
            ex.printStackTrace()
        }
        return macSerial
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     */
    fun getMacFromHardware(): String {
        try {
            val all = NetworkInterface.getNetworkInterfaces()
            while (all.hasMoreElements()) {
                val nif = all.nextElement()
                if (!nif.name.equals("wlan0", ignoreCase = true)) {
                    continue
                }
                val macBytes = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

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