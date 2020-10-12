package com.gitee.android.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Title:
 * <p>
 * Description:
 * </p>
 * @author changbao
 * @date 2020/8/17  15:06
 */

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )


//ListAdapter.
//fun <T, D> submitList(adapter: ListAdapter<T, out RecyclerView.ViewHolder>, list: MutableList<D>?) {
//    super.submitList(if (list != null) ArrayList(list) else null)
//}
