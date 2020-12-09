package com.gitee.android.ui.repo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ando.library.base.BaseMvvmFragment
import com.ando.library.views.RecyclerItemDecoration
import com.gitee.android.R
import com.gitee.android.common.CacheManager
import com.gitee.android.common.isLogin
import com.gitee.android.databinding.FragmentRepoBinding
import com.gitee.android.ui.home.HomeRepoListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Title: RepoFragment
 * <p>
 * Description: 仓库
 * </p>
 * @author ChangBao
 * @date 2020/10/16  15:20
 */
@AndroidEntryPoint
class RepoFragment : BaseMvvmFragment<FragmentRepoBinding>() {

    private val adapter: RepoListAdapter by lazy { RepoListAdapter() }

    private val repoViewModel: RepoViewModel by viewModels()

    override val layoutId: Int = R.layout.fragment_repo

    override fun initView(root: View, savedInstanceState: Bundle?) {

        binding.refreshRecycler.adapter = adapter
        binding.refreshRecycler.addItemDecoration(
            RecyclerItemDecoration(
                baseActivity,
                LinearLayoutManager.HORIZONTAL
            )
        )

        binding.vm = repoViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoViewModel.refresh()
        repoViewModel.getRepos(
            name = CacheManager.getUserName() ?: "",
            access_token = CacheManager.getAccessToken() ?: ""
        ).observe(viewLifecycleOwner)
        { rs ->
            adapter.setData(rs, repoViewModel.isFirstPage())
        }

    }

}