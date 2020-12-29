package com.gitee.android.ui.home

import com.gitee.android.base.BaseRefreshViewModel

/**
 * Title: HomeViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/27  14:59
 */
class HomeViewModel : BaseRefreshViewModel() {

    val recommendArticles = mapListPage(switchPage { repo.getRecommendProjects(it) })

    val hotArticles = mapListPage(switchPage{ repo.getHotProjects(it) })

    val recentlyArticles = mapListPage(switchPage{ repo.getRecentlyProjects(it) })

//    val recentlyArticles = MutableLiveData<List<ArticleEntity>?>()
//    fun getRecentlyArticles(): Job =
//        launch(
//            {
//              recentlyArticles.value = repo.getRecentlyProjects(page.value ?: 1)
//            },
//            {
//                INSTANCE.toastShort(it.message)
//            }
//        )
}