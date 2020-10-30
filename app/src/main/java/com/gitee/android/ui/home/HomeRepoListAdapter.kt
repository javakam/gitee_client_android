package com.gitee.android.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.ando.library.views.recycler.BaseDataBindingAdapter
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
class HomeArticleListAdapter : BaseDataBindingAdapter<ArticleEntity, ItemListHomeArticleBinding>() {

    override val layoutId: Int = R.layout.item_list_home_article

    override fun initView(binding: ItemListHomeArticleBinding, entity: ArticleEntity) {
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
        with(binding) {
            viewModel = ItemArticleItemViewModel(entity)
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
    val tabAvatar = StringUtils.noNull(tab.owner.portrait_url)
}