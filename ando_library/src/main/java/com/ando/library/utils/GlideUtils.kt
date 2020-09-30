package com.ando.library.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Title: GlideUtils
 *
 * Description: 图片加载
 * <pre>
 * 从v3迁移到v4 :  https://muyangmin.github.io/glide-docs-cn/doc/migrating.html
</pre> *
 *
 * 禁用内存缓存功能
 * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
 *
 * DiskCacheStrategy.NONE： 表示不缓存任何内容。
 * DiskCacheStrategy.DATA： 表示只缓存原始图片。
 * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
 * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
 * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
 *
 * 功能包括加载图片，圆形图片，圆角图片，指定圆角图片，模糊图片，灰度图片等等
 * 目前只加了这几个常用功能，其他请参考glide-transformations
 * https://github.com/wasabeef/glide-transformations
 *
 * @author javakam
 * @date 2018/10/19  14:46
 */
object GlideUtils {

    private const val INVALID = -2

    fun load(activity: Activity, imageView: ImageView, url: String?, resId: Int) =
        load(activity, imageView, url, resId, resId, INVALID, INVALID, false, null)

    fun load(
        activity: Activity, imageView: ImageView, url: String?,
        @DrawableRes placeholder: Int, @DimenRes size: Int
    ) =
        load(activity, imageView, url, placeholder, placeholder, size, size, false, null)

    fun load(
        activity: Activity,
        imageView: ImageView,
        url: String?,
        @DrawableRes placeholderRes: Int,
        @DrawableRes errorRes: Int,
        @DimenRes width: Int,
        @DimenRes height: Int,
        signature: Boolean,
        target: CustomTarget<Drawable?>?
    ) {
        var target = target
        if (!checkActivity(activity)) {
            return
        }
        // url = checkUrl(url);
        var options = RequestOptions()
            .centerCrop()
            .placeholder(placeholderRes)
            .error(errorRes)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        //
        if (width != INVALID && height != INVALID) {
            options = options.override(width, height)
        }
//        if (signature) {
//            options = options.signature(new ObjectKey(UUID.randomUUID().toString()));
//        }
        if (target == null) {
            target = object : CustomTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    if (!activity.isDestroyed) {
                        imageView.setImageDrawable(resource)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            }
        }
        Glide.with(imageView.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(options)
            .into(target)
    }

    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param blur 模糊度，一般1-100够了，越大越模糊
     */
    fun loadBlurImage(
        context: Context?,
        url: String?,
        imageView: ImageView?,
        @DrawableRes placeholderRes: Int,
        @DrawableRes errorRe: Int,
        blur: Int
    ) {
        if (!checkActivity(context) || imageView == null) return
        val options = RequestOptions()
            .centerCrop()
            .placeholder(placeholderRes)
            .transform(BlurTransformation(context, blur))
            .error(errorRe) //.priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context!!).load(url).apply(options).into(imageView)
    }

    /**
     * 加载圆形图片
     */
    fun loadCircleImage(
        context: Context?,
        url: String?,
        imageView: ImageView?,
        @DrawableRes placeholderRes: Int,
        @DrawableRes errorRes: Int
    ) {
        if (!checkActivity(context) || imageView == null) return
        val options = RequestOptions()
            .centerCrop()
            .circleCrop() //设置圆形
            .placeholder(placeholderRes)
            .error(errorRes) //.priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context!!).load(url).apply(options).into(imageView)
    }

    /**
     * asGif()    强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     *
     *
     * asBitmap() 只允许加载静态图片，不需要Glide做图片格式的判断。
     * 如果传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param url 例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     */
    private fun loadGif(
        context: Context,
        url: String,
        imageView: ImageView?,
        @DrawableRes placeholderRes: Int,
        @DrawableRes errorRes: Int
    ) {
        if (!checkActivity(context) || imageView == null) return
        val options = RequestOptions()
            .placeholder(placeholderRes)
            .error(errorRes)
        Glide.with(context)
            .load(url)
            .apply(options)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageView)
    }

    private fun checkActivity(context: Context?): Boolean {
        if (context == null) return false

        //Fixed java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
        var activity: Activity? = null
        if (context is Activity) {
            activity = context
        }
        if (activity != null && activity.isDestroyed) {
            Log.e("123", "Glide load failed , activity is destroyed")
            return false
        }
        return true
    }
}