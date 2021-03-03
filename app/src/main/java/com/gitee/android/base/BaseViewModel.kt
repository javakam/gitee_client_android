package com.gitee.android.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ando.library.views.loader.LoadState
import com.gitee.android.http.ApiResponse
import kotlinx.coroutines.launch

/**
 * Title: BaseViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  9:48
 */
open class BaseViewModel : ViewModel() {

    val loaderState = MutableLiveData<LoadState>()
    val isLogin = MutableLiveData<Boolean>()//登录状态
    val toLogin = MutableLiveData<Boolean>()//登录跳转

    init {
        isLogin.value = true
//        isLogin.observeForever {
//            //登录成功，加载进度获取个人信息
//            if (it)
//        }
        loaderState.value = LoadState.LOADING
    }

    open fun reload() {}

    fun changeLoaderState(it: ApiResponse<*>?) {
        if (it?.isSuccessful == false) {
            loaderState.value = LoadState.ERROR
        } else {
            val isEmpty: Boolean = if (it is List<*>) it.isNullOrEmpty() else (it == null)
            if (isEmpty) {
                loaderState.value = LoadState.EMPTY
            } else {
                loaderState.value = LoadState.SUCCESS
            }
        }
    }

    open fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}