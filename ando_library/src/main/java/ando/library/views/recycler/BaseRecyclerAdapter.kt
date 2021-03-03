package ando.library.views.recycler

import android.view.ViewGroup

/**
 * 通用的 RecyclerView 适配器
 *
 * @author javakam
 * @date 2017/9/10 18:30
 */
abstract class BaseRecyclerAdapter<T> : XRecyclerAdapter<T, BaseViewHolder?> {

    protected abstract fun getLayoutId(viewType: Int): Int

    constructor() : super()
    constructor(list: List<T>?) : super(list)
    constructor(data: Array<T>?) : super(data)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(inflateView(parent, getLayoutId(viewType)))
    }
}