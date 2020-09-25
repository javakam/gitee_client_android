package com.ando.toolkit.ext

import android.view.View

/**
 * Title: IntenfaceExt
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/9/25  12:47
 */
interface OnClickListener {
    fun onClick(){}

    fun onClick(view:View)
}
