package com.gitee.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ando.toolkit.ext.toastShort
import com.ando.toolkit.log.L
import com.gitee.android.GiteeApplication.Companion.INSTANCE
import com.gitee.android.base.BaseRefreshViewModel
import com.gitee.android.bean.ArticleEntity
import kotlinx.coroutines.Job

/**
 * Title: HomeViewModel
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  14:59
 */
class HomeViewModel : BaseRefreshViewModel() {

    val hotArticles = MutableLiveData<List<ArticleEntity>?>()
    val recentlyArticles = MutableLiveData<List<ArticleEntity>?>()

    var recommendArticles =
        repo.getRecommendProjects(page.value ?: 1)

    fun getHotArticles(): Job =
        launch(
            {
//                hotArticles.value = repo.getHotProjects(page.value ?: 1)
            },
            {
                INSTANCE.toastShort(it.message)
            }
        )

    fun getRecentlyArticles(): Job =
        launch(
            {
//                recentlyArticles.value = repo.getRecentlyProjects(page.value ?: 1)
            },
            {
                INSTANCE.toastShort(it.message)
            }
        )
}