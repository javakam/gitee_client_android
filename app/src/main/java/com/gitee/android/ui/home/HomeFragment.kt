package com.gitee.android.ui.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import ando.library.base.BaseMvcLazyFragment
import ando.toolkit.log.L
import com.gitee.android.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Title: HomeFragment
 * <p>
 * Description: 首页
 * </p>
 * @author javakam
 * @date 2020/10/12  14:29
 */
@AndroidEntryPoint
class HomeFragment : BaseMvcLazyFragment() {

    private val tabTiles = arrayOf("推荐项目", "热门项目", "最近更新")
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager2: ViewPager2

    override fun initView(savedInstanceState: Bundle?) {
        mTabLayout = rootView.findViewById(R.id.tab_home)
        mViewPager2 = rootView.findViewById(R.id.vp_home)

        val adapter = HomeTabsAdapter(this)
        adapter.addFragments(
            HomeRepoListFragment.create("推荐项目 1", 0),
            HomeRepoListFragment.create("热门项目 2", 1),
            HomeRepoListFragment.create("最近更新 3", 2)
        )
        mViewPager2.adapter = adapter
        mViewPager2.isUserInputEnabled = true
        TabLayoutMediator(mTabLayout, mViewPager2) { tab, position ->
            tab.text = tabTiles[position]
        }.attach()

        L.i("HomeFragment .... init")
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

}