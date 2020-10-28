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
class CustomLoaderView : Loader {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

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