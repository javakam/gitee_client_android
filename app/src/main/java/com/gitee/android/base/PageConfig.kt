package com.gitee.android.base

/**
 * Title: PageConfig
 *
 * Description:
 *
 * @author javakam
 * @date 2019/11/15  16:40
 */
data class PageConfig(
    var enableLazyLoading: Boolean,     //懒加载
    var haveLoader: Boolean,            //是否包含 加载状态视图
    var haveRecyclerView: Boolean,      //是否包含 RecyclerView

    var enableRefresh: Boolean,
    var enableLoadMore: Boolean,

    var usedVLayout: Boolean            //是否使用 vlayout
)