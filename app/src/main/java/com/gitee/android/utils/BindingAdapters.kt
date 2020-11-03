package com.gitee.android.utils

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ando.library.utils.GlideUtils
import com.ando.library.views.loader.LoadState
import com.ando.library.views.loader.Loader
import com.ando.toolkit.ext.hideSoftInput
import com.gitee.android.view.CustomLoaderView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

@BindingAdapter("loadPic", "placeholder", requireAll = true)
fun ImageView.loadPic(url: String?, placeholder: Int) {
    GlideUtils.loadImage(this, url, placeholder)
}

@BindingAdapter("isGone")
fun isGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}

//状态视图切换
@BindingAdapter(value = ["loader_state","loader_reloadListener","loader_stateListener"], requireAll = false)
fun bindCustomLoaderView(
    layout: CustomLoaderView,
    state: LoadState,
    reloadListener: Loader.OnReloadListener?,
    stateListener: Loader.OnStateListener?
) {
    layout.setLoadState(state)
    layout.setOnReloadListener(reloadListener)
    layout.setOnStateListener(stateListener)
}

//状态绑定，控制停止刷新
@BindingAdapter(
    value = ["refreshing", "moreLoading", "hasMore"],
    requireAll = false
)
fun bindSmartRefreshLayout(
    layout: SmartRefreshLayout,
    refreshing: Boolean,
    moreLoading: Boolean,
    hasMore: Boolean
) {
    if (!refreshing) layout.finishRefresh()
    if (!moreLoading) layout.finishLoadMore()
    layout.setEnableLoadMore(hasMore)
}

//控制自动刷新
@BindingAdapter(
    value = ["autoRefresh"]
)
fun bindSmartRefreshLayout(
    smartLayout: SmartRefreshLayout,
    autoRefresh: Boolean
) {
    if (autoRefresh) smartLayout.autoRefresh()
}

//下拉刷新，加载更多
@BindingAdapter(
    value = ["onRefreshListener", "onLoadMoreListener"],
    requireAll = false
)
fun bindListener(
    smartLayout: SmartRefreshLayout,
    refreshListener: OnRefreshListener?,
    loadMoreListener: OnLoadMoreListener?
) {
    smartLayout.setOnRefreshListener(refreshListener)
    smartLayout.setOnLoadMoreListener(loadMoreListener)
}

//绑定软键盘搜索
@BindingAdapter(value = ["searchAction"])
fun bindSearch(et: EditText, callback: () -> Unit) {
    et.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback()
            et.hideSoftInput(v)
            //et.hideKeyboard()
        }
        true
    }
}