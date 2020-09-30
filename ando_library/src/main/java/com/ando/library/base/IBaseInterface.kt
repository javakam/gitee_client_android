package com.ando.library.base

import android.os.Bundle
import android.view.View

interface IBaseInterface {
    fun initView(savedInstanceState: Bundle?)
    fun initListener() {}
    fun initData() {}
    val layoutView: View?
    val layoutId: Int
}