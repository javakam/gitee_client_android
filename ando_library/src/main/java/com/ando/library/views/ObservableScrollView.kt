package com.ando.library.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * <pre>
 * <com.ando.base.views.ObservableScrollView android:id="@+id/observableScrollView" android:layout_width="match_parent" android:layout_height="match_parent" android:background="@color/white" android:descendantFocusability="blocksDescendants" android:fillViewport="true" android:overScrollMode="never" android:paddingBottom="@dimen/dp_40" android:scrollbars="vertical">
</com.ando.base.views.ObservableScrollView></pre> *
 */
class ObservableScrollView : ScrollView {
    interface ScrollViewListener {
        fun onScrollChanged(scrollView: ObservableScrollView?, x: Int, y: Int, oldx: Int, oldy: Int)
    }

    private var scrollViewListener: ScrollViewListener? = null

    constructor(context: Context?) : super(context) {}
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    fun setScrollViewListener(scrollViewListener: ScrollViewListener?) {
        this.scrollViewListener = scrollViewListener
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, x, y, oldx, oldy)
        }
    }
}