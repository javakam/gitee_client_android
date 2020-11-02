package com.gitee.android.ui.home

import androidx.lifecycle.Transformations
import com.gitee.android.base.BaseRefreshViewModel

/**
 * Title: HomeViewModel
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  14:59
 */
class HomeViewModel : BaseRefreshViewModel() {

    // repo.getRecommendProjects(page.value ?: 1)
    private val _recommendArticles = Transformations.switchMap(page){
        repo.getRecommendProjects(it)
    }
    val recommendArticles =mapListPage(_recommendArticles)

    val hotArticles = repo.getHotProjects(page.value ?: 1)
    val recentlyArticles = repo.getRecentlyProjects(page.value ?: 1)

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