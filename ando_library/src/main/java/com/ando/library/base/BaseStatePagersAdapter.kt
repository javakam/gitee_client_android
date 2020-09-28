package com.ando.library.base

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class BaseStatePagersAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(
    fm!!, BEHAVIOR_SET_USER_VISIBLE_HINT
) {
    protected var mTitles: List<String>? = null
    protected var mFragments: List<BaseFragment>? = null
    fun setData(fragments: List<BaseFragment>?, titles: List<String>?) {
        this.mFragments = fragments
        mTitles = titles
    }

    fun updateData(fragments: List<BaseFragment>?, titles: List<String>?) {
        this.mFragments = fragments
        mTitles = titles
        notifyDataSetChanged()
    }

    override fun getItem(i: Int): BaseFragment {
        return mFragments!![i]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles!![position]
    }

    override fun getCount(): Int {
        return if (mTitles == null) 0 else mTitles!!.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}