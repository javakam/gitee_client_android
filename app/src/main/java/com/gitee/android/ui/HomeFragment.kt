package com.gitee.android.ui

import android.os.Bundle
import com.ando.library.base.BaseLazyFragment
import com.gitee.android.R
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
class HomeFragment : BaseLazyFragment() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initLazyData() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

}