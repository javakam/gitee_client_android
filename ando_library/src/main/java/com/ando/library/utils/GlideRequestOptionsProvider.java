package com.ando.library.utils;

import com.bumptech.glide.request.RequestOptions;

/**
 * Title: GlideRequestOptionsProvider
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2020/1/13  14:08
 */
public class GlideRequestOptionsProvider {

    /**
     * Glide+RecyclerView卡在placeHolder视图 , 不显示加载成功图片的问题
     * <pre>
     *     https://www.cnblogs.com/jooy/p/12186977.html
     * </pre>
     */
    public static RequestOptions noAnimate(int placeholder) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .dontAnimate();

        if (placeholder > 0) {
            options = options.placeholder(placeholder);
        }
        return options;
    }

    public static RequestOptions noAnimate(int placeholder, int error) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .dontAnimate();

        if (placeholder > 0) {
            options = options.placeholder(placeholder);
        }

        if (error > 0) {
            options = options.error(error);
        }
        return options;
    }

}