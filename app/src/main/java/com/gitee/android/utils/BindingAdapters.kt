package com.gitee.android.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadPic")
fun ImageView.loadPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else  View.VISIBLE
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