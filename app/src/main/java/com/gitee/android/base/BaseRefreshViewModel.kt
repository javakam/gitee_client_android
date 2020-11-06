package com.gitee.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ando.library.views.loader.LoadState
import com.gitee.android.http.ApiResponse
import com.gitee.android.http.GiteeRepoRemote

/**
 * Title: BaseRefreshViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  9:49
 */
open class BaseRefreshViewModel : BaseViewModel() {

    val repo = GiteeRepoRemote.get()

    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefresh = MutableLiveData<Boolean>()
    val page = MutableLiveData<Int>()

    fun isFirstPage(): Boolean = (page.value == 1)

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

    override fun reload() {
        refresh()
    }

    fun <T> switchPage(block: (page: Int) -> LiveData<ApiResponse<List<T>?>?>): LiveData<ApiResponse<List<T>?>?> {
        return Transformations.switchMap(page) { block(it) }
    }

    fun <T> mapListPage(source: LiveData<ApiResponse<List<T>?>?>): LiveData<List<T>?> {
        return Transformations.map(source) {
            changeLoaderState(it)

            refreshing.value = false
            moreLoading.value = false
            hasMore.value = (it?.body?.isNullOrEmpty() == false)
            it?.body
        }
    }

}