package com.gitee.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ando.library.base.BaseFragment
import com.ando.library.views.RecyclerItemDecoration
import com.gitee.android.R
import com.gitee.android.databinding.FragmentHomeTabBinding
import com.gitee.android.utils.InjectorUtil
import kotlinx.coroutines.Job

/**
 * Title: HomeTabFragment
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  14:12
 */

//"推荐项目", "热门项目", "最近更新"
inline fun HomeTabFragment.fetch(block: () -> Unit) {

}

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

    private lateinit var binding: FragmentHomeTabBinding

    //ViewModelProvider(this,InjectorUtil.getWanAndroidViewModelFactory()).get(WanAndroidViewModel::class.java)
    private val viewModel: HomeViewModel by viewModels {
        InjectorUtil.getViewModelFactory()
    }

    private var jobArticleTabs: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTabBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.resId = R.color.colorPrimary
        binding.lifecycleOwner = this


        val adapter = HomeArticleListAdapter()
        binding.rvArticleTabs.adapter = adapter
        binding.rvArticleTabs.addItemDecoration(
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
        jobArticleTabs = when (arguments?.getInt("pos", 0)) {
            0 -> viewModel.getRecommendArticles(1)
            1 -> viewModel.getHotArticles(1)
            else -> viewModel.getRecentlyArticles(1)
        }

        //xml databinding
        viewModel.recommendArticles.observe(viewLifecycleOwner, Observer { tabs ->
            binding.hasTabs = !tabs.isNullOrEmpty()
            adapter.submitList(tabs)
        })

        //lifecycleScope.launch { }
    }


}