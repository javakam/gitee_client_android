package com.ando.library.views.loader

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.ando.library.utils.noShake
import com.ando.toolkit.ext.dp2px

/**
 * Title:LoaderLayout
 *
 * Description: 网络请求加载页,在请求完毕后通过[.setLoadState]设置请求结果
 *
 * @author javakam
 * @date 2019/11/18 14:51
 */
class LoaderLayout : Loader {

    private var mEmptyTitle: TextView? = null   //空数据标题
    private var mEmptyDes: TextView? = null     //空数据描述
    private var mErrorTitle: TextView? = null   //错误数据标题
    private var mErrorDes: TextView? = null     //错误数据描述

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * 正在加载界面
     */
    override fun createLoadingView(): View? {
        val padding = context.resources.getDimensionPixelOffset(PADDING_SIZE)
        val lParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        val lLayout = LinearLayout(context)
        lLayout.setBackgroundColor(Color.WHITE)
        lLayout.gravity = Gravity.CENTER
        lLayout.layoutParams = lParams
        lLayout.setPadding(padding, padding, padding, padding)
        lLayout.orientation = LinearLayout.VERTICAL
        //int size = getContext().getResources().getDimensionPixelOffset(R.dimen.dp_8);
        val size = 8
        val pb = ProgressBar(context)
        pb.setPadding(size, size, size, size)
        lLayout.addView(pb)
        val tv = TextView(context)
        tv.text = "正在加载，请稍后..."
        tv.setPadding(size, size, size, size)
        tv.gravity = Gravity.CENTER
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.setTextColor(Color.parseColor("#666666"))
        lLayout.addView(tv)
        return lLayout
    }

    /**
     * 失败数据界面的内容
     */
    fun setErrorContent(title: String?, description: String?) {
        if (!TextUtils.isEmpty(title)) {
            mErrorTitle?.text = title
        }
        if (!TextUtils.isEmpty(description)) {
            mErrorDes?.text = description
        }
    }

    /**
     * 没有数据界面的内容
     */
    fun setEmptyContent(title: String?, description: String?) {
        if (!TextUtils.isEmpty(title)) {
            mEmptyTitle?.text = title
        }
        if (!TextUtils.isEmpty(description)) {
            mEmptyDes?.text = description
        }
    }

    /**
     * 没有数据界面
     */
    override fun createEmptyView(): View? {
        val padding = context.resources.getDimensionPixelOffset(PADDING_SIZE)
        val lParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        val lLayout = LinearLayout(context)
        lLayout.setBackgroundColor(Color.WHITE)
        lLayout.gravity = Gravity.CENTER
        lLayout.orientation = LinearLayout.VERTICAL
        lLayout.layoutParams = lParams
        lLayout.setPadding(padding, padding, padding, padding)

        //int size = getContext().getResources().getDimensionPixelOffset(R.dimen.dp_8);
        val size = 8
        //图标
        val icon = ImageView(context)
        icon.setPadding(size, size, size, size)
        //icon.setImageResource(R.drawable.icon_loader_empty);

        //标题
        mEmptyTitle = TextView(context)
        mEmptyTitle?.gravity = Gravity.CENTER
        mEmptyTitle?.setPadding(size, size, size, size)
        mEmptyTitle?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        mEmptyTitle?.text = "返回数据为空"
        mEmptyTitle?.setTextColor(Color.parseColor("#666666"))

        //描述
        mEmptyDes = TextView(context)
        mEmptyDes?.gravity = Gravity.CENTER
        mEmptyDes?.text = "网络请求成功，没有数据记录"

        mEmptyDes?.setLineSpacing(dp2px(5f).toFloat(), 1f)
        mEmptyDes?.setPadding(size, size, size, size)
        mEmptyDes?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        mEmptyDes?.setTextColor(Color.parseColor("#999999"))
        lLayout.addView(icon)
        lLayout.addView(mEmptyTitle)
        lLayout.addView(mEmptyDes)
        return lLayout
    }

    /**
     * 网络异常界面
     */
    override fun createErrorView(): View? {
        val padding = context.resources.getDimensionPixelOffset(PADDING_SIZE)
        val lParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        val layout = LinearLayout(context)
        layout.setBackgroundColor(Color.WHITE)
        layout.gravity = Gravity.CENTER
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = lParams
        layout.setPadding(padding, padding, padding, padding)
        val size = context.resources.getDimensionPixelOffset(PADDING_SIZE)

        //图标
        val icon = ImageView(context)
        icon.setPadding(size, size, size, size)
        //icon.setImageResource(R.drawable.icon_loader_error);

        //标题
        mErrorTitle = TextView(context)
        mErrorTitle?.gravity = Gravity.CENTER
        mErrorTitle?.setPadding(size, size, size, size)
        mErrorTitle?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        mErrorTitle?.text = "网络不给力"
        mErrorTitle?.setTextColor(Color.parseColor("#666666"))

        //描述
        mErrorDes = TextView(context)
        mErrorDes?.gravity = Gravity.CENTER
        mErrorDes?.text = "网络连接失败，请您点击重试"
        mErrorDes?.setPadding(size, size, size, size)
        mErrorDes?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        mErrorDes?.setTextColor(Color.parseColor("#999999"))

        //重试
        layout.noShake {
            reload()
        }
        layout.addView(icon)
        layout.addView(mErrorTitle)
        layout.addView(mErrorDes)
        return layout
    }

    companion object {
        private const val PADDING_SIZE = 30
    }
}