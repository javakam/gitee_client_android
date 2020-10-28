package com.gitee.android.ui.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.ando.library.base.BaseMvcLazyFragment
import com.gitee.android.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Title: HomeFragment
 * <p>
 * Description: 首页
 * </p>
 * @author ChangBao
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
            HomeTabFragment.create("推荐项目 1", 0),
            HomeTabFragment.create("热门项目 2", 1),
            HomeTabFragment.create("最近更新 3", 2)
        )
        mViewPager2.adapter = adapter
        mViewPager2.isUserInputEnabled = true
        TabLayoutMediator(mTabLayout, mViewPager2) { tab, position ->
            tab.text = tabTiles[position]
        }.attach()
    }

    override fun initLazyData() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

}

