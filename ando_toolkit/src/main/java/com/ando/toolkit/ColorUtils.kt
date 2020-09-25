package com.ando.toolkit

import android.graphics.Color
import androidx.annotation.ColorInt
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 颜色辅助工具
 *
 * @author javakam
 * @date 2018/12/27 下午3:00
 */
class ColorUtils private constructor() {

    /**
     * 随机颜色
     */
    class RandomColor internal constructor(alpha: Int, lower: Int, upper: Int) {
        var alpha = 0
            set(value) {
                if (value > 255) {
                    field = 255
                }
                if (value < 0) {
                    field = 0
                }
                field = value
            }

        var lower = 0
            set(value) {
                if (value < 0) {
                    field = 0
                }
                field = value
            }

        var upper = 0
            set(value) {
                if (value > 255) {
                    field = 255
                }
                field = value
            }

        //随机数是前闭  后开
        val color: Int
            get() {
                //随机数是前闭  后开
                val red = lower + Random().nextInt(upper - lower + 1)
                val green = lower + Random().nextInt(upper - lower + 1)
                val blue = lower + Random().nextInt(upper - lower + 1)
                return Color.argb(alpha, red, green, blue)
            }

        init {
            require(upper > lower) { "must be lower < upper" }
            this.alpha=alpha
            this.lower=lower
            this.upper=upper
        }
    }

    companion object {

        fun setColorAlpha(@ColorInt color: Int, alpha: Float): Int {
            return setColorAlpha(color, alpha, true)
        }

        /**
         * 设置颜色的alpha值
         *
         * @param color    需要被设置的颜色值
         * @param alpha    取值为[0,1]，0表示全透明，1表示不透明
         * @param override 覆盖原本的 alpha
         * @return 返回改变了 alpha 值的颜色值
         */
        fun setColorAlpha(
            @ColorInt color: Int,
            alpha: Float,
            override: Boolean
        ): Int {
            val origin = if (override) 0xff else color shr 24 and 0xff
            return color and 0x00ffffff or (alpha * origin).toInt() shl 24
        }

        /**
         * 根据比例，在两个color值之间计算出一个color值
         * **注意该方法是ARGB通道分开计算比例的**
         *
         * @param fromColor 开始的color值
         * @param toColor   最终的color值
         * @param fraction  比例，取值为[0,1]，为0时返回 fromColor， 为1时返回 toColor
         * @return 计算出的color值
         */
        fun computeColor(
            @ColorInt fromColor: Int,
            @ColorInt toColor: Int,
            fraction: Float
        ): Int {
            var fc = fraction
            fc = max(min(fc, 1f), 0f)
            val minColorA = Color.alpha(fromColor)
            val maxColorA = Color.alpha(toColor)
            val resultA = ((maxColorA - minColorA) * fc).toInt() + minColorA
            val minColorR = Color.red(fromColor)
            val maxColorR = Color.red(toColor)
            val resultR = ((maxColorR - minColorR) * fc).toInt() + minColorR
            val minColorG = Color.green(fromColor)
            val maxColorG = Color.green(toColor)
            val resultG = ((maxColorG - minColorG) * fc).toInt() + minColorG
            val minColorB = Color.blue(fromColor)
            val maxColorB = Color.blue(toColor)
            val resultB = ((maxColorB - minColorB) * fc).toInt() + minColorB
            return Color.argb(resultA, resultR, resultG, resultB)
        }

        /**
         * 将 color 颜色值转换为十六进制字符串
         *
         * @param color 颜色值
         * @return 转换后的字符串
         */
        fun colorToString(@ColorInt color: Int): String {
            return String.format("#%08X", color)
        }
        /**
         * 加深颜色
         *
         * @param color  需要加深的颜色
         * @param factor The factor to darken the color.
         * @return darker version of specified color.
         */
        /**
         * 加深颜色
         *
         * @param color 需要加深的颜色
         */
        @JvmOverloads
        fun darker(color: Int, factor: Float = 0.8f): Int {
            return Color.argb(
                Color.alpha(color),
                max((Color.red(color) * factor).toInt(), 0),
                max((Color.green(color) * factor).toInt(), 0),
                max((Color.blue(color) * factor).toInt(), 0)
            )
        }
        /**
         * 变浅颜色
         *
         * @param color  需要变浅的颜色
         * @param factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
         * color white.
         * @return lighter version of the specified color.
         */
        /**
         * 变浅颜色
         *
         * @param color 需要变浅的颜色
         */
        @JvmOverloads
        fun lighter(color: Int, factor: Float = 0.8f): Int {
            val red = ((Color.red(color) * (1 - factor) / 255 + factor) * 255).toInt()
            val green = ((Color.green(color) * (1 - factor) / 255 + factor) * 255).toInt()
            val blue = ((Color.blue(color) * (1 - factor) / 255 + factor) * 255).toInt()
            return Color.argb(Color.alpha(color), red, green, blue)
        }

        /**
         * 是否是深色的颜色
         *
         * @param color
         * @return
         */
        fun isColorDark(@ColorInt color: Int): Boolean {
            val darkness = (1
                    - (0.299 * Color.red(color) + 0.587 * Color.green(
                color
            ) + 0.114 * Color.blue(color))
                    / 255)
            return darkness >= 0.5
        }

        /**
         * @return 获取随机色
         */
        val randomColor: Int
            get() = RandomColor(255, 0, 255).color
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}