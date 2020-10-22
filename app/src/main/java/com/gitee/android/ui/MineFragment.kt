package com.gitee.android.ui

import android.os.Bundle
import com.ando.library.base.BaseLazyFragment
import com.gitee.android.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Title: MineFragment
 * <p>
 * Description: 我的
 * </p>
 * @author javakam
 * @date 2020/10/16  15:20
 */
@AndroidEntryPoint
class MineFragment : BaseLazyFragment() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initLazyData() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

}