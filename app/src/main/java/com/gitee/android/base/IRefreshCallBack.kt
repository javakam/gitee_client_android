package com.gitee.android.base

/**
 * Title: IRefreshCallBack
 *
 *
 * Description:
 *
 *
 * @author javakam
 * @date 2019/11/18  17:05
 */
interface IRefreshCallBack<E> {
    fun onRefreshSuccess(data: List<E>?)
    fun onRefreshFailed(errMsg: String?)
    fun onLoadMoreSuccess(data: List<E>?)
    fun onLoadMoreFailed(errMsg: String?)
}