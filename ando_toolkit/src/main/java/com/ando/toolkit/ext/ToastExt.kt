package com.ando.toolkit.ext

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import com.ando.toolkit.AppUtils.getContext

/**
 * Title: ToastExt
 *
 * Description:
 *
 * @author javakam
 * @date 2019/1/19
 */

fun shortToast(text: String?) {
    ToastUtils.shortToast(text)
}

fun longToast(text: String?) {
    ToastUtils.longToast(text)
}

object ToastUtils {

    private var toast: Toast? = null

    @JvmStatic
    fun shortToast(@StringRes resId: Int) {
        shortToast(
            getContext().resources.getString(resId)
        )
    }

    @JvmStatic
    fun shortToast(text: String?) {
        shortToast(getContext(), text)
    }

    @JvmStatic
    fun shortToast(context: Context?, text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }

    @JvmStatic
    fun longToast(text: String?) {
        longToast(getContext(), text)
    }

    fun longToast(context: Context?, text: String?) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }

}