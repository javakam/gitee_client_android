package com.gitee.android.ui.repo

import ando.library.views.recycler.BaseDataBindingAdapter
import ando.toolkit.ext.copyToClipBoard
import ando.toolkit.ext.noNull
import ando.toolkit.ext.toastShort
import com.gitee.android.R
import com.gitee.android.bean.RepoEntity
import com.gitee.android.databinding.ItemListRepoBinding

/**
 * Title: RepoListAdapter
 *
 * @author javakam
 * @date 2020/8/14  16:25
 */
class RepoListAdapter : BaseDataBindingAdapter<RepoEntity, ItemListRepoBinding>() {

    override val layoutId: Int = R.layout.item_list_repo

    override fun initView(binding: ItemListRepoBinding, entity: RepoEntity) {
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
            vm = ItemRepoViewModel(entity)
            executePendingBindings()
        }
    }
}

//ItemView.xml & Item
class ItemRepoViewModel(tab: RepoEntity) {
    val tabId = tab.id
    val tabLanguage = tab.language
    val tabFullName = tab.full_name
    val tabDesc = tab.description
    val tabAvatar = tab.owner.portrait_url.noNull()
}