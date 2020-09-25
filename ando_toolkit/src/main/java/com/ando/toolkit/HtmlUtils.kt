package com.ando.toolkit

import android.text.Html.ImageGetter
import android.text.Html.TagHandler
import android.text.Spanned
import androidx.core.text.HtmlCompat

/**
 * Title: HtmlUtils
 *
 * Description:
 *
 * @author javakam
 * @date 2019/12/4  10:33
 */
object HtmlUtils {
    /**
     * HTML中 &nbsp;   等6种空格标记
     *
     *
     * https://www.jianshu.com/p/160e5cb0209c
     *
     * <pre>
     * 从API level 24开始，fromHtml(String)被废弃，使用fromHtml(String source, int flags) 代替
     * flags:
     * FROM_HTML_MODE_COMPACT： html块元素之间使用一个换行符分隔
     * FROM_HTML_MODE_LEGACY：  html块元素之间使用两个换行符分隔
    </pre> *
     *
     * @param src
     * @return
     */
    fun fromHtml(src: String?): Spanned? {
        return HtmlCompat.fromHtml(src ?: return null, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    fun fromHtml(src: String?, imageGetter: ImageGetter?, tagHandler: TagHandler?): Spanned? {
        return HtmlCompat.fromHtml(
            src ?: return null,
            HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler
        )
    }
}