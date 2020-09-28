package com.ando.toolkit

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ando.os.utils.R
import com.ando.toolkit.DensityUtils.dp2px
import com.ando.toolkit.DensityUtils.sp2px
import com.ando.toolkit.L.d
import com.ando.toolkit.Utils.getScreenHeight
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.google.android.material.snackbar.SnackbarContentLayout
import java.lang.ref.WeakReference

/**
 * Snackbar工具类
 *
 * @author javakam
 * @date 2018/12/18 下午5:58
 */
class SnackbarUtils {
    private constructor() {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    private constructor(snackbarWeakReference: WeakReference<Snackbar?>?) {
        mSnackbarWeakRef = snackbarWeakReference
    }

    /**
     * 获取 mSnackbar
     */
    val snackbar: Snackbar?
        get() = if (mSnackbarWeakRef != null && mSnackbarWeakRef!!.get() != null) {
            mSnackbarWeakRef!!.get()
        } else {
            null
        }

    /**
     * 设置mSnackbar背景色为  sColorInfo
     */
    fun info(): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(sColorInfo)
        }
        return this
    }

    /**
     * 设置mSnackbar背景色为  sColorConfirm
     */
    fun confirm(): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(sColorConfirm)
        }
        return this
    }

    /**
     * 设置Snackbar背景色为   sColorWarning
     */
    fun warning(): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(sColorWarning)
        }
        return this
    }

    /**
     * 设置Snackbar背景色为   sColorWarning
     */
    fun danger(): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(sColorDanger)
        }
        return this
    }

    /**
     * 设置Snackbar背景色
     */
    fun backColor(@ColorInt backgroundColor: Int): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(backgroundColor)
        }
        return this
    }

    /**
     * 设置TextView(@+id/snackbar_text)的文字颜色
     */
    fun messageColor(@ColorInt messageColor: Int): SnackbarUtils {
        if (snackbar != null) {
            (snackbar!!.view.findViewById<View>(R.id.snackbar_text) as TextView).setTextColor(
                messageColor
            )
        }
        return this
    }

    /**
     * 设置Button(@+id/snackbar_action)的文字颜色
     */
    fun actionColor(@ColorInt actionTextColor: Int): SnackbarUtils {
        if (snackbar != null) {
            (snackbar!!.view.findViewById<View>(R.id.snackbar_action) as Button).setTextColor(
                actionTextColor
            )
        }
        return this
    }

    /**
     * 设置   Snackbar背景色 + TextView(@+id/snackbar_text)的文字颜色 + Button(@+id/snackbar_action)的文字颜色
     */
    fun colors(
        @ColorInt backgroundColor: Int,
        @ColorInt messageColor: Int,
        @ColorInt actionTextColor: Int
    ): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.view.setBackgroundColor(backgroundColor)
            (snackbar!!.view.findViewById<View>(R.id.snackbar_text) as TextView).setTextColor(
                messageColor
            )
            (snackbar!!.view.findViewById<View>(R.id.snackbar_action) as Button).setTextColor(
                actionTextColor
            )
        }
        return this
    }

    /**
     * 设置Snackbar 背景透明度
     */
    fun alpha(alpha: Float): SnackbarUtils {
        var alpha = alpha
        if (snackbar != null) {
            alpha = if (alpha >= 1.0f) 1.0f else Math.max(alpha, 0.0f)
            snackbar!!.view.alpha = alpha
        }
        return this
    }

    /**
     * 设置Snackbar显示的位置
     *
     * @param gravity
     */
    fun gravityFrameLayout(gravity: Int): SnackbarUtils {
        if (snackbar != null) {
            val params = FrameLayout.LayoutParams(
                snackbar!!.view.layoutParams.width,
                snackbar!!.view.layoutParams.height
            )
            params.gravity = gravity
            snackbar!!.view.layoutParams = params
        }
        return this
    }

    /**
     * 设置Snackbar显示的位置,当Snackbar和CoordinatorLayout组合使用的时候
     *
     * @param gravity
     */
    fun gravityCoordinatorLayout(gravity: Int): SnackbarUtils {
        if (snackbar != null) {
            val params = CoordinatorLayout.LayoutParams(
                snackbar!!.view.layoutParams.width,
                snackbar!!.view.layoutParams.height
            )
            params.gravity = gravity
            snackbar!!.view.layoutParams = params
        }
        return this
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * [Snackbar.setAction]
     *
     * @param resId
     * @param listener
     * @return
     */
    fun setAction(@StringRes resId: Int, listener: View.OnClickListener?): SnackbarUtils {
        return if (snackbar != null) {
            setAction(snackbar!!.view.resources.getText(resId), listener)
        } else {
            this
        }
    }

    /**
     * 设置按钮文字内容 及 点击监听
     * [Snackbar.setAction]
     *
     * @param text
     * @param listener
     * @return
     */
    fun setAction(text: CharSequence?, listener: View.OnClickListener?): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.setAction(text, listener)
        }
        return this
    }

    /**
     * 设置 mSnackbar 展示完成 及 隐藏完成 的监听
     *
     * @param setCallback
     * @return
     */
    fun setCallback(setCallback: Snackbar.Callback?): SnackbarUtils {
        if (snackbar != null) {
            snackbar!!.setCallback(setCallback)
        }
        return this
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     *
     * @param leftDrawable
     * @param rightDrawable
     * @return
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun leftAndRightDrawable(
        @DrawableRes leftDrawable: Int?,
        @DrawableRes rightDrawable: Int?
    ): SnackbarUtils {
        return if (snackbar != null) {
            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            if (leftDrawable != null) {
                try {
                    drawableLeft = snackbar!!.view.resources.getDrawable(leftDrawable)
                } catch (e: Exception) {
                }
            }
            if (rightDrawable != null) {
                try {
                    drawableRight = snackbar!!.view.resources.getDrawable(rightDrawable)
                } catch (e: Exception) {
                }
            }
            leftAndRightDrawable(drawableLeft, drawableRight)
        } else {
            this
        }
    }

    /**
     * 设置TextView(@+id/snackbar_text)左右两侧的图片
     */
    fun leftAndRightDrawable(leftDrawable: Drawable?, rightDrawable: Drawable?): SnackbarUtils {
        if (snackbar != null) {
            val message = snackbar!!.view.findViewById<TextView>(R.id.snackbar_text)
            var paramsMessage = message.layoutParams as LinearLayout.LayoutParams
            paramsMessage =
                LinearLayout.LayoutParams(paramsMessage.width, paramsMessage.height, 0.0f)
            message.layoutParams = paramsMessage
            message.compoundDrawablePadding = message.paddingLeft
            val textSize = message.textSize.toInt()
            d(TAG, "textSize:$textSize")
            leftDrawable?.setBounds(0, 0, textSize, textSize)
            rightDrawable?.setBounds(0, 0, textSize, textSize)
            message.setCompoundDrawables(leftDrawable, null, rightDrawable, null)
            val paramsSpace = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            (snackbar!!.view as SnackbarLayout).addView(
                Space(snackbar!!.view.context),
                1,
                paramsSpace
            )
        }
        return this
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居中
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun messageCenter(): SnackbarUtils {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (snackbar != null) {
                val message = snackbar!!.view.findViewById<TextView>(R.id.snackbar_text)
                //View.setTextAlignment需要SDK>=17
                message.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
                message.gravity = Gravity.CENTER
            }
        }
        return this
    }

    /**
     * 设置TextView(@+id/snackbar_text)中文字的对齐方式 居右
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun messageRight(): SnackbarUtils {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (snackbar != null) {
                val message = snackbar!!.view.findViewById<TextView>(R.id.snackbar_text)
                //View.setTextAlignment需要SDK>=17
                message.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
                message.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            }
        }
        return this
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     *
     * @param layoutId 要添加的View的布局文件ID
     * @param index
     * @return
     */
    fun addView(layoutId: Int, index: Int): SnackbarUtils {
        return if (snackbar != null) {
            //加载布局文件新建View
            val addView = LayoutInflater.from(snackbar!!.view.context).inflate(layoutId, null)
            addView(addView, index)
        } else {
            this
        }
    }

    /**
     * 向Snackbar布局中添加View(Google不建议,复杂的布局应该使用DialogFragment进行展示)
     *
     * @param addView
     * @param index
     * @return
     */
    fun addView(addView: View, index: Int): SnackbarUtils {
        if (snackbar != null) {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) //设置新建布局参数
            //设置新建View在Snackbar内垂直居中显示
            params.gravity = Gravity.CENTER_VERTICAL
            addView.layoutParams = params
            //FrameLayout里面套了一个LinearLayout
            val contentLayout = (snackbar!!.view as SnackbarLayout)
                .getChildAt(0) as SnackbarContentLayout
            contentLayout.addView(addView, index)
        }
        return this
    }

    /**
     * 设置Snackbar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     *
     * @param margin
     * @return
     */
    fun margins(margin: Int): SnackbarUtils {
        return if (snackbar != null) {
            margins(margin, margin, margin, margin)
        } else {
            this
        }
    }

    /**
     * 设置Snackbar布局的外边距
     * 注:经试验发现,调用margins后再调用 gravityFrameLayout,则margins无效.
     * 为保证margins有效,应该先调用 gravityFrameLayout,在 show() 之前调用 margins
     */
    fun margins(left: Int, top: Int, right: Int, bottom: Int): SnackbarUtils {
        if (snackbar != null) {
            val params = snackbar!!.view.layoutParams
            (params as MarginLayoutParams).setMargins(left, top, right, bottom)
            snackbar!!.view.layoutParams = params
        }
        return this
    }

    /**
     * 通过SnackBar现在的背景,获取其设置圆角值时候所需的GradientDrawable实例
     *
     * @param backgroundOri
     * @return
     */
    private fun getRadiusDrawable(backgroundOri: Drawable): GradientDrawable? {
        var background: GradientDrawable? = null
        if (backgroundOri is GradientDrawable) {
            background = backgroundOri
        } else if (backgroundOri is ColorDrawable) {
            val backgroundColor = backgroundOri.color
            background = GradientDrawable()
            background.setColor(backgroundColor)
        }
        return background
    }

    /**
     * 设置Snackbar布局的圆角半径值
     *
     * @param radius 圆角半径
     */
    fun radius(radius: Float): SnackbarUtils {
        var radius = radius
        if (snackbar != null) {
            //将要设置给mSnackbar的背景
            val background = getRadiusDrawable(snackbar!!.view.background)
            if (background != null) {
                radius = if (radius <= 0) 12 else radius
                background.cornerRadius = radius
                snackbar!!.view.setBackgroundDrawable(background)
            }
        }
        return this
    }

    /**
     * 设置Snackbar布局的圆角半径值及边框颜色及边框宽度
     */
    fun radius(radius: Int, strokeWidth: Int, @ColorInt strokeColor: Int): SnackbarUtils {
        var radius = radius
        var strokeWidth = strokeWidth
        if (snackbar != null) {
            //将要设置给mSnackbar的背景
            val background = getRadiusDrawable(snackbar!!.view.background)
            if (background != null) {
                radius = if (radius <= 0) 12 else radius
                strokeWidth =
                    if (strokeWidth <= 0) 1 else if (strokeWidth >= snackbar!!.view.findViewById<View>(
                            R.id.snackbar_text
                        ).paddingTop
                    ) 2 else strokeWidth
                background.cornerRadius = radius.toFloat()
                background.setStroke(strokeWidth, strokeColor)
                snackbar!!.view.setBackgroundDrawable(background)
            }
        }
        return this
    }

    /**
     * 计算单行的Snackbar的高度值(单位 pix)
     */
    private fun calculateSnackBarHeight(): Int {
        /*
        <TextView
                android:id="@+id/snackbar_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:paddingTop="@dimen/design_snackbar_padding_vertical"
                android:paddingBottom="@dimen/design_snackbar_padding_vertical"
                android:paddingLeft="@dimen/design_snackbar_padding_horizontal"
                android:paddingRight="@dimen/design_snackbar_padding_horizontal"
                android:textAppearance="@style/TextAppearance.Design.Snackbar.Message"
                android:maxLines="@integer/design_snackbar_text_max_lines"
                android:layout_gravity="center_vertical|left|start"
                android:ellipsize="end"
                android:textAlignment="viewStart"/>
        */
        //文字高度+paddingTop+paddingBottom : 14sp + 14dp*2
        val snackBarHeight = dp2px(snackbar!!.view.context, 28f) + sp2px(
            snackbar!!.view.context, 14f
        )
        d(TAG, "直接获取MessageView高度:" + snackbar!!.view.findViewById<View>(R.id.snackbar_text).height)
        return snackBarHeight
    }

    /**
     * 设置Snackbar显示在指定View的上方
     * 注:暂时仅支持单行的Snackbar,因为[SnackbarUtils.calculateSnackBarHeight]暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    fun above(
        targetView: View,
        contentViewTop: Int,
        marginLeft: Int,
        marginRight: Int
    ): SnackbarUtils {
        var marginLeft = marginLeft
        var marginRight = marginRight
        if (snackbar != null) {
            marginLeft = Math.max(marginLeft, 0)
            marginRight = Math.max(marginRight, 0)
            val locations = IntArray(2)
            targetView.getLocationOnScreen(locations)
            d(TAG, "距离屏幕左侧:" + locations[0] + "==距离屏幕顶部:" + locations[1])
            val snackbarHeight = calculateSnackBarHeight()
            d(TAG, "Snackbar高度:$snackbarHeight")
            //必须保证指定View的顶部可见 且 单行Snackbar可以完整的展示
            if (locations[1] >= contentViewTop + snackbarHeight) {
                gravityFrameLayout(Gravity.BOTTOM)
                val params = snackbar!!.view.layoutParams
                (params as MarginLayoutParams).setMargins(
                    marginLeft,
                    0,
                    marginRight,
                    snackbar!!.view.resources.displayMetrics.heightPixels - locations[1]
                )
                snackbar!!.view.layoutParams = params
            }
        }
        return this
    }

    //CoordinatorLayout
    fun aboveCoordinatorLayout(
        targetView: View,
        contentViewTop: Int,
        marginLeft: Int,
        marginRight: Int
    ): SnackbarUtils {
        var marginLeft = marginLeft
        var marginRight = marginRight
        if (snackbar != null) {
            marginLeft = Math.max(marginLeft, 0)
            marginRight = Math.max(marginRight, 0)
            val locations = IntArray(2)
            targetView.getLocationOnScreen(locations)
            d(TAG, "距离屏幕左侧:" + locations[0] + "==距离屏幕顶部:" + locations[1])
            val snackbarHeight = calculateSnackBarHeight()
            d(TAG, "Snackbar高度:$snackbarHeight")
            //必须保证指定View的顶部可见 且 单行Snackbar可以完整的展示
            if (locations[1] >= contentViewTop + snackbarHeight) {
                gravityCoordinatorLayout(Gravity.BOTTOM)
                val params = snackbar!!.view.layoutParams
                (params as MarginLayoutParams).setMargins(
                    marginLeft,
                    0,
                    marginRight,
                    snackbar!!.view.resources.displayMetrics.heightPixels - locations[1]
                )
                snackbar!!.view.layoutParams = params
            }
        }
        return this
    }

    /**
     * 设置Snackbar显示在指定View的下方
     * 注:暂时仅支持单行的Snackbar,因为[SnackbarUtils.calculateSnackBarHeight]暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    fun bellow(
        targetView: View,
        contentViewTop: Int,
        marginLeft: Int,
        marginRight: Int
    ): SnackbarUtils {
        var marginLeft = marginLeft
        var marginRight = marginRight
        if (snackbar != null) {
            marginLeft = Math.max(marginLeft, 0)
            marginRight = Math.max(marginRight, 0)
            val locations = IntArray(2)
            targetView.getLocationOnScreen(locations)
            val snackbarHeight = calculateSnackBarHeight()
            val screenHeight = getScreenHeight(snackbar!!.view.context)
            //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
                //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
                if (locations[1] + targetView.height >= contentViewTop && locations[1] + targetView.height + snackbarHeight + 2 <= screenHeight) {
                    gravityFrameLayout(Gravity.BOTTOM)
                    val params = snackbar!!.view.layoutParams
                    (params as MarginLayoutParams).setMargins(
                        marginLeft,
                        0,
                        marginRight,
                        screenHeight - (locations[1] + targetView.height + snackbarHeight + 2)
                    )
                    snackbar!!.view.layoutParams = params
                }
            } else {
                if (locations[1] + targetView.height >= contentViewTop && locations[1] + targetView.height + snackbarHeight <= screenHeight) {
                    gravityFrameLayout(Gravity.BOTTOM)
                    val params = snackbar!!.view.layoutParams
                    (params as MarginLayoutParams).setMargins(
                        marginLeft,
                        0,
                        marginRight,
                        screenHeight - (locations[1] + targetView.height + snackbarHeight)
                    )
                    snackbar!!.view.layoutParams = params
                }
            }
        }
        return this
    }

    fun bellowCoordinatorLayout(
        targetView: View,
        contentViewTop: Int,
        marginLeft: Int,
        marginRight: Int
    ): SnackbarUtils {
        var marginLeft = marginLeft
        var marginRight = marginRight
        if (snackbar != null) {
            marginLeft = Math.max(marginLeft, 0)
            marginRight = Math.max(marginRight, 0)
            val locations = IntArray(2)
            targetView.getLocationOnScreen(locations)
            val snackbarHeight = calculateSnackBarHeight()
            val screenHeight = getScreenHeight(snackbar!!.view.context)
            //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
                //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
                if (locations[1] + targetView.height >= contentViewTop && locations[1] + targetView.height + snackbarHeight + 2 <= screenHeight) {
                    gravityCoordinatorLayout(Gravity.BOTTOM)
                    val params = snackbar!!.view.layoutParams
                    (params as MarginLayoutParams).setMargins(
                        marginLeft,
                        0,
                        marginRight,
                        screenHeight - (locations[1] + targetView.height + snackbarHeight + 2)
                    )
                    snackbar!!.view.layoutParams = params
                }
            } else {
                if (locations[1] + targetView.height >= contentViewTop && locations[1] + targetView.height + snackbarHeight <= screenHeight) {
                    gravityCoordinatorLayout(Gravity.BOTTOM)
                    val params = snackbar!!.view.layoutParams
                    (params as MarginLayoutParams).setMargins(
                        marginLeft,
                        0,
                        marginRight,
                        screenHeight - (locations[1] + targetView.height + snackbarHeight)
                    )
                    snackbar!!.view.layoutParams = params
                }
            }
        }
        return this
    }

    /**
     * 显示 Snackbar
     */
    fun show() {
        if (snackbar != null) {
            snackbar!!.show()
        } else {
            d(TAG, "已经被回收")
        }
    }

    companion object {
        private const val TAG = "SnackbarUtils"

        //设置Snackbar背景颜色
        private var sColorInfo = -0xd6611d
        private var sColorConfirm = -0xb34fb2
        private var sColorWarning = -0x13ffb
        private var sColorDanger = -0xbbcca

        //工具类当前持有的Snackbar实例
        private var mSnackbarWeakRef: WeakReference<Snackbar?>? = null

        /**
         * 设置信息的背景颜色
         *
         * @param colorInfo
         */
        fun setColorInfo(colorInfo: Int) {
            sColorInfo = colorInfo
        }

        /**
         * 设置确定的背景颜色
         *
         * @param colorConfirm
         */
        fun setColorConfirm(colorConfirm: Int) {
            sColorConfirm = colorConfirm
        }

        /**
         * 设置警告的背景颜色
         */
        fun setColorWarning(colorWarning: Int) {
            sColorWarning = colorWarning
        }

        /**
         * 设置危险的背景颜色
         */
        fun setColorDanger(colorDanger: Int) {
            sColorDanger = colorDanger
        }

        /**
         * 初始化Snackbar实例
         * 展示时间:Snackbar.LENGTH_SHORT
         */
        fun Short(view: View?, message: String?): SnackbarUtils {
            /*
        <view xmlns:android="http://schemas.android.com/apk/res/android"
          class="android.support.design.widget.Snackbar$SnackbarLayout"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_gravity="bottom"
          android:theme="@style/ThemeOverlay.AppCompat.Dark"
          style="@style/Widget.Design.Snackbar" />
        <style name="Widget.Design.Snackbar" parent="android:Widget">
            <item name="android:minWidth">@dimen/design_snackbar_min_width</item>
            <item name="android:maxWidth">@dimen/design_snackbar_max_width</item>
            <item name="android:background">@drawable/design_snackbar_background</item>
            <item name="android:paddingLeft">@dimen/design_snackbar_padding_horizontal</item>
            <item name="android:paddingRight">@dimen/design_snackbar_padding_horizontal</item>
            <item name="elevation">@dimen/design_snackbar_elevation</item>
            <item name="maxActionInlineWidth">@dimen/design_snackbar_action_inline_max_width</item>
        </style>
        <shape xmlns:android="http://schemas.android.com/apk/res/android"
            android:shape="rectangle">
            <corners android:radius="@dimen/design_snackbar_background_corner_radius"/>
            <solid android:color="@color/design_snackbar_background_color"/>
        </shape>
        <color name="design_snackbar_background_color">#323232</color>
        */
            return SnackbarUtils(
                WeakReference(
                    Snackbar.make(
                        view!!,
                        message!!,
                        Snackbar.LENGTH_SHORT
                    )
                )
            ).backColor(-0xcdcdce)
        }

        /**
         * 初始化Snackbar实例
         * 展示时间:Snackbar.LENGTH_LONG
         */
        fun Long(view: View?, message: String?): SnackbarUtils {
            return SnackbarUtils(
                WeakReference(
                    Snackbar.make(
                        view!!,
                        message!!,
                        Snackbar.LENGTH_LONG
                    )
                )
            ).backColor(-0xcdcdce)
        }

        /**
         * 初始化Snackbar实例
         * 展示时间:Snackbar.LENGTH_INDEFINITE
         */
        fun Indefinite(view: View?, message: String?): SnackbarUtils {
            return SnackbarUtils(
                WeakReference(
                    Snackbar.make(
                        view!!,
                        message!!,
                        Snackbar.LENGTH_INDEFINITE
                    )
                )
            ).backColor(-0xcdcdce)
        }

        /**
         * 初始化Snackbar实例
         * 展示时间:duration 毫秒
         *
         * @param duration 展示时长(毫秒)
         */
        fun Custom(view: View?, message: String?, duration: Int): SnackbarUtils {
            return SnackbarUtils(
                WeakReference(
                    Snackbar.make(
                        view!!,
                        message!!,
                        Snackbar.LENGTH_SHORT
                    ).setDuration(duration)
                )
            ).backColor(-0xcdcdce)
        }
    }
}