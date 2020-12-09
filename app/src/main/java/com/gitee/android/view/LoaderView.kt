package com.gitee.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ando.library.utils.noShake
import com.ando.library.views.loader.LoadState
import com.ando.library.views.loader.Loader
import com.gitee.android.R

/**
 * Title:CustomLoaderView
 *
 * Description: 状态页
 *
 * @author javakam
 * @date 2019/11/18 14:53
 */
class LoaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,defStyleRes: Int = 0
) : Loader(context, attrs, defStyleAttr,defStyleRes ) {

    init {
        setBackgroundResource(android.R.color.transparent)
    }

    override fun createLoadingView(): View? {
        return inflate(context, R.layout.layout_loader, null)
    }

    override fun createEmptyView(): View? {
        //TextView tvEmpty = view.findViewById(R.id.tv_empty);
        return inflate(context, R.layout.layout_loader_empty, null)
    }

    override fun createErrorView(): View? {
        val view = inflate(context, R.layout.layout_loader_error, null)
        view.findViewById<View>(R.id.btn_reload).noShake {
            reload()
        }
        return view
    }
}