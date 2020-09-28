package com.ando.toolkit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Proxy
import android.telephony.TelephonyManager
import android.text.TextUtils

/**
 * NetWork Utils
 *
 * **Attentions**
 *  * You should add **android.permission.ACCESS_NETWORK_STATE** in manifest, to get network status.
 *
 *
 * @author [Trinea](http://www.trinea.cn) 2014-11-03
 */
object NetUtils {
    const val NETWORK_TYPE_WIFI = "wifi"
    const val NETWORK_TYPE_3G = "eg"
    const val NETWORK_TYPE_2G = "2g"
    const val NETWORK_TYPE_WAP = "wap"
    const val NETWORK_TYPE_UNKNOWN = "unknown"
    const val NETWORK_TYPE_DISCONNECT = "disconnect"

    /**
     * Get network type
     */
    fun getNetworkType(context: Context): Int {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.type ?: -1
    }

    /**
     * Get network type name
     */
    fun getNetworkTypeName(context: Context): String {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo
        var type = NETWORK_TYPE_DISCONNECT
        if (manager == null || manager.activeNetworkInfo.also { networkInfo = it!! } == null) {
            return type
        }
        if (networkInfo.isConnected) {
            val typeName = networkInfo.typeName
            type = if ("WIFI".equals(typeName, ignoreCase = true)) {
                NETWORK_TYPE_WIFI
            } else if ("MOBILE".equals(typeName, ignoreCase = true)) {
                val proxyHost = Proxy.getDefaultHost()
                if (TextUtils.isEmpty(proxyHost)) if (isFastMobileNetwork(context)) NETWORK_TYPE_3G else NETWORK_TYPE_2G else NETWORK_TYPE_WAP
            } else {
                NETWORK_TYPE_UNKNOWN
            }
        }
        return type
    }

    /**
     * Whether is fast mobile network
     */
    private fun isFastMobileNetwork(context: Context): Boolean {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                ?: return false
        return when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_LTE -> true
            TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
            else -> false
        }
    }

    /**
     * 网络是否可用
     * @param context
     */
    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return null != info && info.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null && mMobileNetworkInfo.isAvailable) {
                return mMobileNetworkInfo.isConnected
            }
        }
        return false
    }

    /**
     * 判断WIFI网络是否可用
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable) {
                return mWiFiNetworkInfo.isConnected
            }
        }
        return false
    }
}