package com.gitee.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import ando.library.views.loader.Loader
import ando.toolkit.ext.noShake
import com.gitee.android.R

/**
 * # LoaderView 状态页
 *
 * @author javakam
 * @date 2019/11/18 14:53
 */
class LoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : Loader(context, attrs, defStyleAttr, defStyleRes) {

    init {
        setBackgroundResource(android.R.color.transparent)
    }

    override fun createLoadingView(): View? {
        return inflate(context, R.layout.layout_loader, null)
    }

    override fun createEmptyView(): View? {
        return inflate(context, R.layout.layout_loader_empty, null)
    }

    override fun createErrorView(): View? {
        val view = inflate(context, R.layout.layout_loader_error, null)
        view.findViewById<View>(R.id.btn_reload).noShake(1000L) {
            reload()
        }
        return view
    }
}