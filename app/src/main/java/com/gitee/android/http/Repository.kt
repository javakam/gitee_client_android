package com.gitee.android.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ando.toolkit.ACache
import ando.toolkit.StringUtils
import com.gitee.android.bean.LoginEntity
import com.gitee.android.common.CLIENT_ID
import com.gitee.android.common.CLIENT_SECRET
import com.gitee.android.common.CacheManager
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
 * @author javakam
 * @date 2020/10/27  15:00
 */

interface IRepository

class GiteeRepoRLocal : IRepository

class GiteeRepoRemote : IRepository {

    private val api = ApiService.get()
    private val apiV3 = ApiService.getV3()

    fun login(account: String, password: String): LiveData<ApiResponse<LoginEntity>?>? {
        return api.login(
            mutableMapOf(
                "grant_type" to "password",
                "username" to account,
                "password" to password,
                "client_id" to CLIENT_ID,
                "client_secret" to CLIENT_SECRET,
                //"scope" to "projects user_info issues notes"
            )
        )
    }

    fun loginByToken(): LiveData<ApiResponse<LoginEntity>?>? {
        val refreshToken: String = CacheManager.getLoginData()?.refresh_token ?: ""
        return api.refreshToken(
            mutableMapOf(
                "grant_type" to "refresh_token",
                "refresh_token" to refreshToken
            )
        )
    }

    fun getUserInfo(access_token: String) = api.getUserInfo(access_token = access_token)

    fun getRepositories(name: String, access_token: String, page: Int) =
        api.getRepositories(name, access_token, page)

    fun getRecommendProjects(page: Int) = apiV3.getRecommendProjects(page)

    fun getHotProjects(page: Int) = apiV3.getHotProjects(page)

    fun getRecentlyProjects(page: Int) = apiV3.getRecentlyProjects(page)

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
        private var usbUtils: GiteeRepoRemote? = null

        fun get(): GiteeRepoRemote {
            if (usbUtils == null) {
                synchronized(GiteeRepoRemote::class.java) {
                    if (usbUtils == null) {
                        usbUtils = GiteeRepoRemote()
                    }
                }
            }
            return usbUtils!!
        }
    }

}