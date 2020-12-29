package com.ando.toolkit.ext

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView

/**
 * Title:
 * <p>
 * Description: 为view添加OnGlobalLayoutListener监听并在测量完成后自动取消监听同时执行[globalAction]函数
 * </p>
 * @author javakam
 * @date 2020/9/30  15:16
 */
inline fun <T : View> T.afterMeasured(crossinline globalAction: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                globalAction()
            }
        }
    })
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

var View.leftPadding: Int
    inline get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

var View.topPadding: Int
    inline get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

var View.rightPadding: Int
    inline get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

var View.bottomPadding: Int
    inline get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)

inline var TextView.isSelectable: Boolean
    get() = isTextSelectable
    set(value) = setTextIsSelectable(value)