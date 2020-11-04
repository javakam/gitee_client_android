package com.gitee.android.utils

import android.app.Activity
import android.content.Intent
import androidx.core.app.ShareCompat

/**
 * Title: CommonUtils
 * <p>
 * Description:
 * </p>
 * @author changbao
 * @date 2020/8/17  14:52
 */

fun createShareIntent(activity: Activity, shareText: String?) {
    val shareIntent = ShareCompat.IntentBuilder.from(activity)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}