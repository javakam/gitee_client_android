package com.gitee.android.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gitee.android.base.BaseRefreshViewModel
import com.gitee.android.bean.RepoEntity

/**
 * Title: RepoViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/11/6  10:38
 */
class RepoViewModel : BaseRefreshViewModel() {

    fun getRepos(name: String, access_token: String): LiveData<List<RepoEntity>?> {
        return mapListPage(Transformations.switchMap(page) {
            repo.getRepositories(name, access_token, it)
        })
    }

}