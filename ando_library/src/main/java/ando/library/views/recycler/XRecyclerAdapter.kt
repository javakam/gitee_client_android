package ando.library.views.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * # 基础的RecyclerView适配器
 *
 * @author javakam
 * @date 2019-08-11 16:12
 */
abstract class XRecyclerAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH> {
    /**
     * 数据源
     */
    protected val mData: MutableList<T> = ArrayList()

    /**
     * 点击监听
     */
    private var mClickListener: OnItemClickListener? = null

    /**
     * 长按监听
     */
    private var mLongClickListener: OnItemLongClickListener? = null

    /**
     * 当前点击的条目
     */
    var selectPosition = -1
        private set

    interface OnItemLongClickListener {
        fun onItemLongClick(viewHolder: BaseViewHolder, position: Int): Boolean
    }

    interface OnItemClickListener {
        fun onItemClick(viewHolder: BaseViewHolder, position: Int)
    }

    constructor()

    constructor(list: List<T>?) {
        if (!list.isNullOrEmpty()) {
            mData.addAll(list)
        }
    }

    constructor(data: Array<T>?) {
        if (!data.isNullOrEmpty()) {
            mData.addAll(listOf(*data))
        }
    }

    /**
     * 构建自定义的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract fun getViewHolder(parent: ViewGroup, viewType: Int): VH

    /**
     * 绑定数据
     *
     * @param holder
     * @param position 索引
     * @param item     列表项
     */
    protected abstract fun bindData(holder: VH, position: Int, item: T)

    /**
     * 加载布局获取控件
     *
     * @param parent   父布局
     * @param layoutId 布局ID
     * @return
     */
    protected fun inflateView(parent: ViewGroup, @LayoutRes layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val holder = getViewHolder(parent, viewType)
        if (mClickListener != null) {
            holder.itemView.setOnClickListener {
                mClickListener?.onItemClick(holder, holder.layoutPosition)
            }
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener {
                mLongClickListener?.onItemLongClick(holder, holder.layoutPosition)
                true
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindData(holder, position, mData[position])
    }

    /**
     * 获取列表项
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T? {
        return if (checkPosition(position)) mData[position] else null
    }

    private fun checkPosition(position: Int): Boolean {
        return position >= 0 && position <= mData.size - 1
    }

    fun isEmpty(): Boolean = (itemCount == 0)

    override fun getItemCount(): Int = (mData.size)

    /**
     * 给指定位置添加一项
     *
     * @param pos
     * @param item
     * @return
     */
    fun add(pos: Int, item: T): XRecyclerAdapter<*, *> {
        mData.add(pos, item)
        notifyItemInserted(pos)
        return this
    }

    /**
     * 在列表末端增加一项
     *
     * @param item
     * @return
     */
    fun add(item: T): XRecyclerAdapter<*, *> {
        mData.add(item)
        notifyItemInserted(mData.size - 1)
        return this
    }

    /**
     * 删除列表中指定索引的数据
     *
     * @param pos
     * @return
     */
    fun delete(pos: Int): XRecyclerAdapter<*, *> {
        mData.removeAt(pos)
        notifyItemRemoved(pos)
        return this
    }

    /**
     * 刷新列表中指定位置的数据
     *
     * @param pos
     * @param item
     * @return
     */
    fun refresh(pos: Int, item: T): XRecyclerAdapter<*, *> {
        mData[pos] = item
        notifyItemChanged(pos)
        return this
    }

    /**
     * 刷新列表数据
     */
    fun refresh(collection: Collection<T>?): XRecyclerAdapter<*, *> {
        if (collection != null) {
            mData.clear()
            mData.addAll(collection)
            selectPosition = -1
            notifyDataSetChanged()
        }
        return this
    }

    /**
     * 刷新列表数据
     */
    fun refresh(array: Array<T>?): XRecyclerAdapter<*, *> {
        if (array != null && array.isNotEmpty()) {
            mData.clear()
            mData.addAll(listOf(*array))
            selectPosition = -1
            notifyDataSetChanged()
        }
        return this
    }

    /**
     * 加载更多
     */
    fun loadMore(collection: Collection<T>?): XRecyclerAdapter<*, *> {
        if (collection != null) {
            mData.addAll(collection)
            notifyDataSetChanged()
        }
        return this
    }

    /**
     * 加载更多
     */
    fun loadMore(array: Array<T>?): XRecyclerAdapter<*, *> {
        if (array != null && array.isNotEmpty()) {
            mData.addAll(listOf(*array))
            notifyDataSetChanged()
        }
        return this
    }

    /**
     * 添加一个
     */
    fun load(item: T?): XRecyclerAdapter<*, *> {
        if (item != null) {
            mData.add(item)
            notifyDataSetChanged()
        }
        return this
    }

    /**
     * 设置列表项点击监听
     *
     * @param listener
     * @return
     */
    fun setOnItemClickListener(listener: OnItemClickListener?): XRecyclerAdapter<*, *> {
        mClickListener = listener
        return this
    }

    /**
     * 设置列表项长按监听
     *
     * @param listener
     * @return
     */
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?): XRecyclerAdapter<*, *> {
        mLongClickListener = listener
        return this
    }

    /**
     * 设置当前列表的选中项
     *
     * @param selectPosition
     * @return
     */
    fun setSelectPosition(selectPosition: Int): XRecyclerAdapter<*, *> {
        this.selectPosition = selectPosition
        notifyDataSetChanged()
        return this
    }

    /**
     * 获取当前列表选中项
     *
     * @return 当前列表选中项
     */
    val selectItem: T?
        get() = getItem(selectPosition)

    /**
     * 清除数据
     */
    fun clear() {
        if (!isEmpty()) {
            mData.clear()
            selectPosition = -1
            notifyDataSetChanged()
        }
    }
}