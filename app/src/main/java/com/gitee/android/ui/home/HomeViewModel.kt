package com.gitee.android.ui.home

import androidx.lifecycle.MutableLiveData
import com.ando.toolkit.ext.toastShort
import com.ando.toolkit.log.L
import com.gitee.android.GiteeApplication.Companion.INSTANCE
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.http.GiteeRepo
import com.gitee.android.viewmodel.BaseViewModel
import kotlinx.coroutines.Job

/**
 * Title: HomeViewModel
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  14:59
 */
class HomeViewModel internal constructor(private val repo: GiteeRepo) : BaseViewModel() {


    val recommendArticles = MutableLiveData<List<ArticleEntity>>()
    val hotArticles = MutableLiveData<List<ArticleEntity>>()
    val recentlyArticles = MutableLiveData<List<ArticleEntity>>()

    fun getRecommendArticles(page: Int): Job =
        launch(
            {
                recommendArticles.value = repo.requestRecommendProjects(page)
            },
            {
                L.e(it.message)
                INSTANCE.toastShort(it.message)
            }
        )

    fun getHotArticles(page: Int): Job =
        launch(
            {
                hotArticles.value = repo.requestHotProjects(page)
            },
            {
                INSTANCE.toastShort(it.message)
            }
        )

    fun getRecentlyArticles(page: Int): Job =
        launch(
            {
                recentlyArticles.value = repo.requestRecentlyProjects(page)
            },
            {
                INSTANCE.toastShort(it.message)
            }
        )
}