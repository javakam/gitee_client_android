package com.ando.library.utils.glide

import com.bumptech.glide.request.RequestOptions

/**
 * Title: GlideRequestOptionsProvider
 *
 * Description:
 *
 * @author javakam
 * @date 2020/1/13  14:08
 */
object GlideOptionsProvider {
    /**
     * Glide+RecyclerView卡在placeHolder视图 , 不显示加载成功图片的问题
     * <pre>
     * https://www.cnblogs.com/jooy/p/12186977.html
    </pre> *
     */
    fun noAnimate(placeholder: Int): RequestOptions {
        var options = RequestOptions()
            .centerCrop()
            .dontAnimate()
        if (placeholder > 0) {
            options = options.placeholder(placeholder)
        }
        return options
    }

    fun noAnimate(placeholder: Int, error: Int): RequestOptions {
        var options = RequestOptions()
            .centerCrop()
            .dontAnimate()
        if (placeholder > 0) {
            options = options.placeholder(placeholder)
        }
        if (error > 0) {
            options = options.error(error)
        }
        return options
    }
}