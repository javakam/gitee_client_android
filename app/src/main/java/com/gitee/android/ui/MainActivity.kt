package com.gitee.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.ando.library.base.BaseFragment
import com.gitee.android.R
import com.gitee.android.common.switchFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val PAGE_NEWS = R.id.rb_news
        const val PAGE_EPAPER = R.id.rb_epaper
        const val PAGE_POINT = R.id.rb_point
    }

    private lateinit var mRgNav: RadioGroup
    private val mHomeFragment = HomeFragment()
    private val mFragments: MutableList<BaseFragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavBar()
    }

    private fun initNavBar() {

        mRgNav = findViewById(R.id.indicator_nav_main)
        //初始化导航
        mRgNav.setOnCheckedChangeListener { group, checkedId ->
            val index = when (checkedId) {
                PAGE_NEWS -> 0
                PAGE_EPAPER -> 1
                PAGE_POINT -> 2
                else -> 3 //PAGE_MINE -> R.id.rb_mine
            }

            //checkedId 从1开始计算
            //L.e("onCheckedChanged checkedId : $checkedId  index=$index  mFragments=${mFragments.size}")

            group?.check(checkedId)
            switchFragment(supportFragmentManager, mFragments, index)
        }

        mFragments.add(mHomeFragment)

    }
}