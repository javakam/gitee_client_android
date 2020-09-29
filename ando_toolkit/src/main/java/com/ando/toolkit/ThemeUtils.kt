package com.ando.toolkit

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

/**
 * 主题工具
 *
 * @author javakam
 * @date 2018/11/14 下午1:46
 */
object ThemeUtils {

    @ColorInt
    fun getDisabledColor(context: Context): Int {
        val primaryColor = resolveColor(context, R.attr.textColorPrimary)
        val disabledColor = if (isColorDark(primaryColor)) Color.BLACK else Color.WHITE
        return adjustAlpha(disabledColor, 0.3f)
    }

    @ColorInt
    fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    @ColorInt
    fun resolveColor(context: Context, @AttrRes attr: Int): Int = resolveColor(context, attr, 0)

    @ColorInt
    fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int): Int {
        val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
        return try {
            a.getColor(0, fallback)
        } finally {
            a.recycle()
        }
    }

    fun getColorFromAttrRes(attrRes: Int, defaultValue: Int, context: Context): Int {
        val a = context.obtainStyledAttributes(intArrayOf(attrRes))
        return try {
            a.getColor(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    fun resolveFloat(context: Context, attrRes: Int): Float {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue.float
    }

    fun resolveInt(context: Context, attrRes: Int, defaultValue: Int = 0): Int {
        val a = context.obtainStyledAttributes(intArrayOf(attrRes))
        return try {
            a.getInt(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    fun resolveFloat(context: Context, attrRes: Int, defaultValue: Float): Float {
        val a = context.obtainStyledAttributes(intArrayOf(attrRes))
        return try {
            a.getFloat(0, defaultValue)
        } finally {
            a.recycle()
        }
    }

    // Try to resolve the colorAttr attribute.
    fun resolveActionTextColorStateList(
        context: Context, @AttrRes colorAttr: Int, fallback: ColorStateList
    ): ColorStateList {
        val a = context.theme.obtainStyledAttributes(intArrayOf(colorAttr))
        return try {
            val value = a.peekValue(0) ?: return fallback
            if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && value.type <= TypedValue.TYPE_LAST_COLOR_INT
            ) {
                getActionTextStateList(context, value.data)
            } else {
                val stateList = a.getColorStateList(0)
                stateList ?: fallback
            }
        } finally {
            a.recycle()
        }
    }

    // Get the specified color resource, creating a ColorStateList if the resource
    // points to a color values.
    fun getActionTextColorStateList(context: Context, @ColorRes colorId: Int): ColorStateList {
        val value = TypedValue()
        context.resources.getValue(colorId, value, true)
        return if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
            && value.type <= TypedValue.TYPE_LAST_COLOR_INT
        ) {
            getActionTextStateList(context, value.data)
        } else {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                context.resources.getColorStateList(colorId)
            } else {
                context.getColorStateList(colorId)
            }
        }
    }

    /**
     * Returns a color associated with a particular resource ID
     *
     * Starting in [android.os.Build.VERSION_CODES.M], the returned color will be styled for
     * the specified Context's theme.
     *
     * @param colorId The desired resource identifier, as generated by the aapt tool. This integer
     * encodes the package, type, and resource entry. The values 0 is an invalid identifier.
     * @return A single color values in the form 0xAARRGGBB.
     */
    @ColorInt
    fun getColor(context: Context?, @ColorRes colorId: Int): Int =
        context?.let {
            ContextCompat.getColor(it, colorId)
        } ?: 0

    fun resolveString(context: Context, @AttrRes attr: Int): String {
        val v = TypedValue()
        context.theme.resolveAttribute(attr, v, true)
        return v.string as String
    }

    fun resolveString(theme: Theme, @AttrRes attr: Int): String {
        val v = TypedValue()
        theme.resolveAttribute(attr, v, true)
        return v.string as String
    }

    fun resolveDrawable(context: Context, @AttrRes attr: Int): Drawable? {
        return resolveDrawable(context, attr, null)
    }

    private fun resolveDrawable(
        context: Context,
        @AttrRes attr: Int,
        fallback: Drawable?
    ): Drawable? {
        val array = context.theme.obtainStyledAttributes(intArrayOf(attr))
        return try {
            var drawable: Drawable? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = array.getDrawable(0)
            } else {
                val id = array.getResourceId(0, -1)
                if (id != -1) {
                    drawable = AppCompatResources.getDrawable(context, id)
                }
            }
            if (drawable == null && fallback != null) {
                drawable = fallback
            }
            drawable
        } finally {
            array.recycle()
        }
    }

    fun resolveDimension(context: Context, @AttrRes attr: Int, fallback: Int = -1): Int {
        val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
        return try {
            a.getDimensionPixelSize(0, fallback)
        } finally {
            a.recycle()
        }
    }

    fun resolveBoolean(
        context: Context,
        @AttrRes attr: Int,
        fallback: Boolean = false
    ): Boolean {
        val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
        return try {
            a.getBoolean(0, fallback)
        } finally {
            a.recycle()
        }
    }

    fun isColorDark(@ColorInt color: Int): Boolean {
        val darkness =
            (1 - (0.299 * Color.red(color) + 0.587 * Color.green(color)
                    + 0.114 * Color.blue(color)) / 255)
        return darkness >= 0.5
    }

    fun setBackgroundCompat(view: View, d: Drawable?) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(d)
        } else {
            view.background = d
        }

    fun getActionTextStateList(context: Context, newPrimaryColor: Int): ColorStateList {
        var newPrimaryColor = newPrimaryColor
        val fallBackButtonColor = resolveColor(context, R.attr.textColorPrimary)
        if (newPrimaryColor == 0) {
            newPrimaryColor = fallBackButtonColor
        }
        val states = arrayOf(intArrayOf(-R.attr.state_enabled), intArrayOf())
        val colors = intArrayOf(adjustAlpha(newPrimaryColor, 0.4f), newPrimaryColor)
        return ColorStateList(states, colors)
    }

    fun getColorArray(context: Context, @ArrayRes array: Int): IntArray? {
        if (array == 0) return null
        val ta = context.resources.obtainTypedArray(array)
        val colors = IntArray(ta.length())
        for (i in 0 until ta.length()) {
            colors[i] = ta.getColor(i, 0)
        }
        ta.recycle()
        return colors
    }

    fun <T> isIn(find: T, ary: Array<T>?): Boolean {
        if (ary == null || ary.isEmpty()) {
            return false
        }
        for (item in ary) {
            if (item == find) {
                return true
            }
        }
        return false
    }

}