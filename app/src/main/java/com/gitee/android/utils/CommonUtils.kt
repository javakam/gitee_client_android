package com.gitee.android.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.core.app.ShareCompat
import com.gitee.android.GiteeApplication

/**
 * Title: CommonUtils
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/8/17  14:52
 */

fun noNull(s: String?): String = if (s.isNullOrBlank()) "" else s

fun setClipDate(text: String?) {
    val cm: ClipboardManager? =
        GiteeApplication.instance.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager?
    if (cm != null) {
        cm.setPrimaryClip(ClipData.newPlainText(null, text))//参数一：标签，可为空，参数二：要复制到剪贴板的文本
        if (cm.hasPrimaryClip()) {
            cm.primaryClip?.getItemAt(0)?.text
        }
    }
}

fun createShareIntent(activity: Activity, shareText: String?) {
    val shareIntent = ShareCompat.IntentBuilder.from(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}