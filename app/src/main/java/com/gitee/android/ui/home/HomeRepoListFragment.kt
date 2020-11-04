package com.gitee.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ando.library.base.BaseFragment
import com.ando.library.views.RecyclerItemDecoration
import com.gitee.android.R
import com.gitee.android.databinding.FragmentHomeRepoListBinding
import kotlinx.coroutines.Job

/**
 * Title: HomeTabFragment
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  14:12
 */
class HomeTabFragment : BaseFragment() {
    /// R.layout.fragment_home_tab
    companion object {
        fun create(arg: String, position: Int): HomeTabFragment {
            val fragment = HomeTabFragment()
            val bundle = Bundle()
            bundle.putString("show", arg)
            bundle.putInt("pos", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentHomeRepoListBinding

    //ViewModelProvider(this,InjectorUtil.getWanAndroidViewModelFactory()).get(WanAndroidViewModel::class.java)
    private val viewModel: HomeViewModel by viewModels()

    private var jobArticleTabs: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeRepoListBinding.inflate(inflater, container, false)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        val adapter = HomeArticleListAdapter()
        binding.refreshRecycler.adapter = adapter
        binding.refreshRecycler.addItemDecoration(
            RecyclerItemDecoration(
                baseActivity,
                LinearLayoutManager.HORIZONTAL
            )
        )

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: HomeArticleListAdapter) {
        jobArticleTabs?.cancel()
        viewModel.refresh()
        when (arguments?.getInt("pos", 0)) {
            0 -> {
                //jobArticleTabs = viewModel.getRecommendArticles()
                //xml databinding
                viewModel.recommendArticles.observe(viewLifecycleOwner)
                { rs ->
                    binding.hasTabs = (rs?.isNullOrEmpty() == false)
                    adapter.setData(rs, viewModel.isFirstPage())
                }
            }
            1 -> {
                viewModel.hotArticles.observe(viewLifecycleOwner)
                { rs ->
                    binding.hasTabs = (rs?.isNullOrEmpty() == false)
                    adapter.setData(rs, viewModel.isFirstPage())
                }
            }
            else -> {
                //jobArticleTabs = viewModel.getRecentlyArticles()
                viewModel.recentlyArticles.observe(viewLifecycleOwner)
                { rs ->
                    binding.hasTabs = (rs?.isNullOrEmpty() == false)
                    adapter.setData(rs, viewModel.isFirstPage())
                }
            }
        }
        //lifecycleScope.launch { }
    }


}