package com.gitee.android.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ando.library.utils.GlideUtils

@BindingAdapter("loadPic", "placeholder", requireAll = true)
fun ImageView.loadPic(url: String?, placeholder: Int) {
    GlideUtils.loadImage(this, url, placeholder)
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}