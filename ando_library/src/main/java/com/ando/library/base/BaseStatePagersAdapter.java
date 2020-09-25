package com.ando.library.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public abstract class BaseStatePagersAdapter extends FragmentStatePagerAdapter {

    protected List<String> mTitles;
    protected List<BaseFragment> mFragments;

    public BaseStatePagersAdapter(FragmentManager fm) {
        //https://blog.csdn.net/c6E5UlI1N/article/details/90307961
        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);//BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    }

    public void setData(List<BaseFragment> fragments, List<String> titles) {
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    public void updateData(List<BaseFragment> fragments, List<String> titles) {
        this.mFragments = fragments;
        this.mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public BaseFragment getItem(int i) {
        return mFragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}