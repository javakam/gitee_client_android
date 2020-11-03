package com.gitee.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ando.library.base.BaseViewModel
import com.ando.library.views.loader.LoadState
import com.gitee.android.http.ApiResponse
import com.gitee.android.http.GiteeRepo
import com.gitee.android.view.CustomLoaderView

/**
 * Title: BaseRefreshViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  9:49
 */
open class BaseRefreshViewModel : BaseViewModel() {

    val repo = GiteeRepo.get()

    val loaderState = MutableLiveData<LoadState>()
    val page = MutableLiveData<Int>()
    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefresh = MutableLiveData<Boolean>()

    init {
        loaderState.value = LoadState.LOADING
    }

    fun autoRefresh() {
        autoRefresh.value = true
    }

    fun refresh() {
        page.value = 1
        refreshing.value = true
    }

    fun loadMore() {
        page.value = (page.value ?: 1) + 1
        moreLoading.value = true
    }

    fun loaderReload() {
        refresh()
    }

    fun <T> mapListPage(source: LiveData<ApiResponse<List<T>>?>): LiveData<List<T>> {
        return Transformations.map(source) {
            changeLoaderState(it)

            refreshing.value = false
            moreLoading.value = false
            hasMore.value = (it?.body?.isNullOrEmpty() == false)
            it?.body
        }
    }

    private fun <T> changeLoaderState(it: ApiResponse<List<T>>?) {
        it?.apply {
            if (!isSuccessful) {
                loaderState.value = LoadState.ERROR
            } else {
                if (body?.isNullOrEmpty() == true) {
                    loaderState.value = LoadState.EMPTY
                } else {
                    loaderState.value = LoadState.SUCCESS
                }
            }

        }
    }

}