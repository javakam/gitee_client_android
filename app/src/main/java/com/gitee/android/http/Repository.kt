package com.gitee.android.http

import com.ando.toolkit.ext.no
import com.gitee.android.bean.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Title: Repository
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  15:00
 */

interface IRepository

class GiteeRepo private constructor(private val network: NetWork) : IRepository {

     suspend fun requestRecommendProjects(page: Int): List<ArticleEntity>? =
        withContext(Dispatchers.IO) {
            network.getRecommendProjects(page)
        }

     suspend fun requestHotProjects(page: Int): List<ArticleEntity>? =
        withContext(Dispatchers.IO) {
            network.getHotProjects(page)
        }

     suspend fun requestRecentlyProjects(page: Int): List<ArticleEntity>? =
        withContext(Dispatchers.IO) {
            network.getRecentlyProjects(page)
        }

    companion object {

        private lateinit var instance: GiteeRepo

        fun getInstance(network: NetWork): GiteeRepo {
            Companion::instance.isInitialized.no {
                synchronized(GiteeRepo::class.java) {
                    Companion::instance.isInitialized.no {
                        instance = GiteeRepo(network)
                    }
                }
            }
            return instance
        }
    }

}