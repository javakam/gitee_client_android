package com.gitee.android.base

import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * <pre>
 * onLoadMore 传入false表示刷新失败
 * mRefreshLayout.finishRefresh(2000,false) or mRefreshLayout.finishLoadMore(2000,false);
</pre> *
 */
interface IPageLazyRefresh : OnRefreshLoadMoreListener {
    fun initConfig(): PageConfig?
    fun initRefresh(refreshLayout: RefreshLayout?) {}
}