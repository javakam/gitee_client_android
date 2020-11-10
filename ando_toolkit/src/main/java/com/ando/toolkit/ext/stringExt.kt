package com.ando.toolkit.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.ando.toolkit.AppUtils

/**
 * Title: String Extension
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  10:07
 */

fun String.copyToClipBoard() {
    val cm: ClipboardManager? =
        AppUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager?
    if (cm != null) {
        cm.setPrimaryClip(ClipData.newPlainText(null, this))//参数一：标签，可为空，参数二：要复制到剪贴板的文本
        if (cm.hasPrimaryClip()) {
            cm.primaryClip?.getItemAt(0)?.text
        }
    }
}