package com.gitee.android.ui.home

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

    val recommendArticles = MutableLiveData<List<ArticleEntity>>()
    val hotArticles = MutableLiveData<List<ArticleEntity>>()
    val recentlyArticles = MutableLiveData<List<ArticleEntity>>()

    fun getRecommendArticles(): Job =
        launch(
            {
//                recommendArticles.value =
//                    transformPage()
//                    repo.getRecommendProjects(page.value ?: 1)
            },
            {
                L.e(it.message)
                INSTANCE.toastShort(it.message)
            }
        )

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