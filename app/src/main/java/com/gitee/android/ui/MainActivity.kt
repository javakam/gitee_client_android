package com.gitee.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.ando.library.base.BaseFragment
import com.gitee.android.R
import com.gitee.android.common.switchFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val NAV_HOME = R.id.rb_home
        const val NAV_DYNAMIC = R.id.rb_dynamic
    }

    private lateinit var mRgNav: RadioGroup
    private val mHomeFragment by lazy { HomeFragment() }
    private val mDynamicFragment by lazy { DynamicFragment() }
    private val mMineFragment by lazy { MineFragment() }
    private val mFragments: MutableList<BaseFragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavBar()
    }

    private fun initNavBar() {
        mRgNav = findViewById(R.id.indicator_nav_main)
        mRgNav.setOnCheckedChangeListener { group, checkedId ->
            val index = when (checkedId) {
                NAV_HOME -> 0
                NAV_DYNAMIC -> 1
                else -> 2
            }
            //L.e("onCheckedChanged checkedId : $checkedId  index=$index  mFragments=${mFragments.size}")
            group?.check(checkedId)
            switchFragment(supportFragmentManager, mFragments, index)
        }

        mFragments.apply {
            add(mHomeFragment)
            add(mDynamicFragment)
            add(mMineFragment)
        }

    }

}