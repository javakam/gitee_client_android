package com.gitee.android.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class HomeTabsAdapter : FragmentStateAdapter {
    private val mFragments: MutableList<Fragment>

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity) {
        this.mFragments = ArrayList()
    }

    constructor(fragment: Fragment) : super(fragment) {
        this.mFragments = ArrayList()
    }

    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    ) {
        this.mFragments = ArrayList()
    }

    override fun createFragment(position: Int): Fragment = mFragments[position]

    override fun getItemCount(): Int = mFragments.size

    //////////////////////////////////////////////////////

    fun addFragments(vararg fragments: Fragment) {
        mFragments.addAll(fragments)
    }

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
        notifyDataSetChanged()
    }

    fun removeFragment() {
        if (mFragments.isNotEmpty()) {
            mFragments.removeAt(mFragments.size - 1)
            notifyDataSetChanged()
        }
    }
}