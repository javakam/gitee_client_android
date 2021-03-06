package com.gitee.android.ui.home

import androidx.recyclerview.widget.DiffUtil
import ando.library.views.recycler.BaseDataBindingAdapter
import ando.toolkit.ext.copyToClipBoard
import ando.toolkit.ext.noNull
import ando.toolkit.ext.toastShort
import com.gitee.android.R
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.databinding.ItemListHomeArticleBinding

/**
 * Title: HomeArticleListAdapter
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/8/14  16:25
 */
class HomeRepoListAdapter : BaseDataBindingAdapter<ArticleEntity, ItemListHomeArticleBinding>() {

    override val layoutId: Int = R.layout.item_list_home_article

    override fun initView(binding: ItemListHomeArticleBinding, entity: ArticleEntity) {
        binding.setClickListener { v ->
            binding.vm?.tabId?.let { tabId ->
                //toArticleListFragment(view, tabId)
                v.toastShort("Click $tabId")
            }
        }
        binding.constTabName.setOnLongClickListener {
            binding.tvTabName.text.toString().copyToClipBoard()
            it.toastShort("已复制")
            true
        }
        with(binding) {
            vm = ItemArticleItemViewModel(entity)
            executePendingBindings()
        }
    }
}

private class WxArticleTabsAdapterDiffCallback : DiffUtil.ItemCallback<ArticleEntity>() {
    override fun areItemsTheSame(
        oldItem: ArticleEntity,
        newItem: ArticleEntity
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ArticleEntity,
        newItem: ArticleEntity
    ): Boolean = oldItem.name == newItem.name
}

//ItemView.xml & Item
class ItemArticleItemViewModel(tab: ArticleEntity) {
    val tabId = tab.id
    val tabName = tab.name
    val tabDesc = tab.description
    val tabAvatar = tab.owner.portrait_url.noNull()
}