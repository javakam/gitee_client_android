package com.gitee.android.http

import com.gitee.android.bean.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Title: Repository
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  15:00
 */

interface IRepository

class GiteeRepo : IRepository {

    private val api = ApiService.get()

    fun getRecommendProjects(page: Int) = api.getRecommendProjects(page)

    fun getHotProjects(page: Int) = api.getHotProjects(page)

    fun getRecentlyProjects(page: Int) = api.getRecentlyProjects(page)

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {
        private var usbUtils: GiteeRepo? = null

        fun get(): GiteeRepo {
            if (usbUtils == null) {
                synchronized(GiteeRepo::class.java) {
                    if (usbUtils == null) {
                        usbUtils = GiteeRepo()
                    }
                }
            }
            return usbUtils!!
        }
    }

}