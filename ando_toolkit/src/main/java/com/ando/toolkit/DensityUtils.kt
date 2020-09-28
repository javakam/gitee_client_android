package com.ando.toolkit

import android.content.Context
import android.util.DisplayMetrics

/**
 * 屏幕密度工具类
 *
 * @author javakam
 * @date 2018/12/18 上午12:15
 */
class DensityUtils private constructor() {
    companion object {
        /**
         * DisplayMetrics
         */
        val displayMetrics: DisplayMetrics
            get() = ResUtils.getResources().displayMetrics

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         *
         * @param dpValue 尺寸dip
         * @return 像素值
         */
        fun dp2px(dpValue: Float): Int {
            val scale = ResUtils.getResources().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         *
         * @param context 上下文
         * @param dpValue 尺寸dip
         * @return 像素值
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         *
         * @param pxValue 尺寸像素
         * @return DIP值
         */
        fun px2dp(pxValue: Float): Int {
            val scale = ResUtils.getResources().displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         *
         * @param context 上下文
         * @param pxValue 尺寸像素
         * @return DIP值
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
         *
         * @param pxValue 尺寸像素
         * @return SP值
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale = ResUtils.getResources().displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
         *
         * @param pxValue 尺寸像素
         * @return SP值
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 sp 的单位 转成为 px
         *
         * @param spValue SP值
         * @return 像素值
         */
        fun sp2px(spValue: Float): Int {
            val fontScale = ResUtils.getResources().displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 sp 的单位 转成为 px
         *
         * @param spValue SP值
         * @return 像素值
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 获取屏幕分辨率
         *
         * @return 屏幕分辨率幕高度
         */
        val screenDpi: Int
            get() = displayMetrics.densityDpi

        /**
         * 获取真实屏幕密度
         *
         * @param context 上下文【注意，Application和Activity的屏幕密度是不一样的】
         * @return
         */
        fun getRealDpi(context: Context): Int {
            val metric = context.resources.displayMetrics
            val xdpi = metric.xdpi
            val ydpi = metric.ydpi
            return ((xdpi + ydpi) / 2.0f + 0.5f).toInt()
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}