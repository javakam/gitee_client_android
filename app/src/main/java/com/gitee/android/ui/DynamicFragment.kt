package com.gitee.android.ui

import android.os.Bundle
import com.ando.library.base.BaseLazyFragment
import com.gitee.android.R

/**
 * Title: DynamicFragment
 * <p>
 * Description: 动态
 * </p>
 * @author javakam
 * @date 2020/10/16  15:20
 */
class DynamicFragment : BaseLazyFragment() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initLazyData() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_dynamic

}