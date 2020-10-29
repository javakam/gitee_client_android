package com.gitee.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ando.toolkit.StringUtils
import com.ando.toolkit.ext.clipBoard
import com.ando.toolkit.ext.toastShort
import com.gitee.android.R
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.databinding.ItemListHomeArticleBinding

/**
 * Title: HomeArticleListAdapter
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/8/14  16:25
 */
class HomeArticleListAdapter : ListAdapter<ArticleEntity, HomeArticleListAdapter.ViewHolder>(
    WxArticleTabsAdapterDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_home_article, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemListHomeArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { v ->
                binding.viewModel?.tabId?.let { tabId ->
                    //toArticleListFragment(view, tabId)
                    v.toastShort("Click $tabId")
                }
            }

            binding.constTabName.setOnLongClickListener {
                binding.tvTabName.text.toString().clipBoard()
                it.toastShort("已复制")
                true
            }
        }

        fun bind(tab: ArticleEntity) {
            with(binding) {
                viewModel = ItemArticleItemViewModel(tab)
                executePendingBindings()
            }
        }
    }
}

private class WxArticleTabsAdapterDiffCallback : DiffUtil.ItemCallback<ArticleEntity>() {

    override fun areItemsTheSame(
        oldItem: ArticleEntity,
        newItem: ArticleEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ArticleEntity,
        newItem: ArticleEntity
    ): Boolean {
        return oldItem.name == newItem.name
    }
}

//ItemView.xml & Item
class ItemArticleItemViewModel(tab: ArticleEntity) {

    val tabId = tab.id
    val tabName = tab.name
    val tabDesc = tab.description
    val tabAvatar = StringUtils.noNull(tab.owner.portrait_url)

}