package com.gitee.android.ui

import android.os.Bundle
import com.ando.library.base.BaseLazyFragment
import com.ando.library.base.BaseMvcLazyFragment
import com.gitee.android.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Title: MineFragment
 * <p>
 * Description: 我的
 * </p>
 * @author ChangBao
 * @date 2020/10/16  15:20
 */
@AndroidEntryPoint
class MineFragment : BaseMvcLazyFragment() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initLazyData() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

}