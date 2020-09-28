package com.ando.toolkit

import java.math.BigDecimal

/**
 * Title:NumberUtils
 *
 *
 * Description:数据工具类
 *
 *
 * @author javakam
 * Date 2018/8/17 14:40
 */
object NumberUtils {
    private fun setScale(value: Float, scale: Int): Float {
        val decimal: BigDecimal = BigDecimal(value)
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    private fun setScale(value: Double, scale: Int): Double {
        val decimal = BigDecimal(value)
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun parseInteger(value: String?): Int {
        val result = 0
        if (value != null) {
            try {
                return value.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun parseInteger(value: Number?): Int {
        val result = 0
        return value?.toInt() ?: result
    }

    fun parseLong(value: String?): Long {
        val result: Long = 0
        if (value != null) {
            try {
                return value.toLong()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun parseLong(value: Number?): Long {
        val result: Long = 0
        return value?.toLong() ?: result
    }

    fun parseFloat(value: String?, scale: Int): Float {
        var result = 0.00f
        if (value != null) {
            try {
                result = value.toFloat()
                return setScale(result, scale)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun parseFloat(value: Number?, scale: Int): Float {
        val result = 0.00f
        return if (value != null) {
            setScale(value.toFloat(), scale)
        } else result
    }

    fun parseDouble(value: String?, scale: Int): Double {
        var result = 0.00
        if (value != null) {
            try {
                result = value.toDouble()
                return setScale(result, scale)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun parseDouble(value: Number?, scale: Int): Double {
        val result = 0.00
        return if (value != null) {
            setScale(value.toDouble(), scale)
        } else result
    }
}