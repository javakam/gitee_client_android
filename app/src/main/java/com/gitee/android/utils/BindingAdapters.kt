package com.gitee.android.utils

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ando.library.utils.GlideRequestOptionsProvider
import com.ando.library.utils.GlideUtils
import com.ando.toolkit.ResUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gitee.android.R
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("loadPic")
fun ImageView.loadPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

@BindingAdapter("loadPic")
fun CircleImageView.loadPic(url: String?) {
    if (url != null && url.startsWith("http")) {
        GlideUtils.loadImage(
            this,
            url,
            GlideRequestOptionsProvider.noAnimate(R.mipmap.ic_head_default)
        )
    } else{
        val bitmap=BitmapFactory.decodeResource(resources,R.mipmap.ic_head_default)
        GlideUtils.loadImage(this, null,BitmapDrawable(resources,bitmap))
    }
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

//@BindingAdapter("bind:colorSchemeResources")
//fun SwipeRefreshLayout.colorSchemeResources(resId: Int) {
//    setColorSchemeResources(resId)
//}
//
//@BindingAdapter("bind:showForecast")
//fun LinearLayout.showForecast(weather: Weather?) = weather?.let {
//    removeAllViews()
//    for (forecast in it.forecastList) {
//        val view = LayoutInflater.from(context).inflate(R.layout.forecast_item, this, false)
//        DataBindingUtil.bind<ForecastItemBinding>(view)?.forecast = forecast
//        addView(view)
//    }
//}