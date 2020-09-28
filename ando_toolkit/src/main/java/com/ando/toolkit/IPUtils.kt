package com.ando.toolkit

import android.util.Log
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

/**
 * Title:IPUtils
 *
 *
 * Description: IP地址工具类
 *
 *
 * @author javakam
 * @date 2020-1-9
 */
object IPUtils {
    /**
     * 获取本机IPv6地址
     */
    val iPv6: String?
        get() {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIpAddr = en.nextElement().inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet6Address) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("IP Address", ex.toString())
            }
            return null
        }

    private fun intToIp(ipAddress: Int): String {
        return (ipAddress and 0xFF).toString() + "." +
                (ipAddress shr 8 and 0xFF) + "." +
                (ipAddress shr 16 and 0xFF) + "." +
                (ipAddress shr 24 and 0xFF)
    }

    /**
     * 获取本机IPv4地址；null：无网络连接
     */
    val iPv4: String?
        get() = try {
            var networkInterface: NetworkInterface
            var inetAddress: InetAddress
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                networkInterface = en.nextElement()
                val enumIpAddr = networkInterface.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && !inetAddress.isLinkLocalAddress) {
                        return inetAddress.hostAddress
                    }
                }
            }
            null
        } catch (ex: SocketException) {
            ex.printStackTrace()
            null
        }
}