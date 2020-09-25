package com.ando.library.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * <pre>
 *           <com.ando.base.views.ObservableScrollView
 *                 android:id="@+id/observableScrollView"
 *                 android:layout_width="match_parent"
 *                 android:layout_height="match_parent"
 *                 android:background="@color/white"
 *                 android:descendantFocusability="blocksDescendants"
 *                 android:fillViewport="true"
 *                 android:overScrollMode="never"
 *                 android:paddingBottom="@dimen/dp_40"
 *                 android:scrollbars="vertical">
 * </pre>
 */
public class ObservableScrollView extends ScrollView {

    public interface ScrollViewListener {

        /**
         * @param scrollView
         * @param x
         * @param y
         * @param oldx
         * @param oldy
         */
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
