package com.gitee.android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ando.library.base.BaseViewModel
import com.gitee.android.http.ApiResponse
import com.gitee.android.http.GiteeRepo

/**
 * Title: BaseRefreshViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  9:49
 */
open class BaseRefreshViewModel : BaseViewModel() {

    protected val page = MutableLiveData<Int>()

    val repo = GiteeRepo.get()
    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefresh = MutableLiveData<Boolean>()

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

    /**
     * 处理分页数据
     */
    fun <T> mapListPage(source: LiveData<ApiResponse<List<T>>?>): LiveData<List<T>> {
        return Transformations.map(source) {
            refreshing.value = false
            moreLoading.value = false
            hasMore.value = (it?.body?.isNullOrEmpty() == false)
            it?.body
        }
    }

}