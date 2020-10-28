package com.ando.library.base

interface IBaseLazyInterface {
    /**
     * (!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
     */
    fun prepareFetchData() {}

    /**
     * (!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
     */
    fun prepareFetchData(forceUpdate: Boolean) {}
    fun initLazyData()
}