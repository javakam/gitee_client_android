package com.ando.toolkit

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

/**
 * Title:WindowUtils
 *
 * Description:Window窗体工具类
 *
 * @author javakam
 * @date 2018/9/23 18:54
 */
object WindowUtils {
    /**
     * 设置状态栏背景，一个Acitivty中只能调用一次，仅支持5.0以上
     * v21
     * <item name="android:windowTranslucentStatus">false</item>
     * <item name="android:windowTranslucentNavigation">true</item>
     * <item name="android:statusBarColor">@android:color/transparent</item>
     *
     * @param activity
     * @param drawable
     */
    fun setStatusBarDrawable(activity: Activity, drawable: Drawable?) {
        //7.0以上移除状态栏阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                @SuppressLint("PrivateApi") val decorViewClazz =
                    Class.forName("com.android.internal.policy.DecorView")
                val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.setInt(activity.window.decorView, Color.TRANSPARENT) //改为透明
            } catch (e: Exception) {
                Log.e(WindowUtils::class.java.simpleName, e.message!!)
            }
        }
        if (drawable == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val rootView = activity.findViewById<View>(R.id.content) as FrameLayout
            val count = rootView.childCount
            if (count > 0) {
                val layout = rootView.getChildAt(0)
                val statusBarHeight = getStatusBarHeight(activity)
                val layoutParams = layout.layoutParams as FrameLayout.LayoutParams
                layoutParams.topMargin = statusBarHeight
                val statusBarView: ImageView
                if (count > 1) {
                    statusBarView = rootView.getChildAt(1) as ImageView
                } else {
                    statusBarView = ImageView(activity)
                    val viewParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight
                    )
                    statusBarView.scaleType = ImageView.ScaleType.FIT_XY
                    statusBarView.layoutParams = viewParams
                    rootView.addView(statusBarView)
                }
                statusBarView.setImageDrawable(drawable)
            }
        }
    }

    /**
     * 利用反射获取顶部状态栏高度
     */
    fun getStatusBarHeight(activity: Activity): Int {
        var result = 0 //获取状态栏高度的资源id
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 隐藏底部导航栏
     */
    fun hideStatusBarBottom(window: Window) {
        val decorView = window.decorView
        val option = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = option
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                decorView.systemUiVisibility = option
            }
        }
    }

    /**
     * 全屏显示
     */
    fun hideStatusBar(window: Window?) {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}