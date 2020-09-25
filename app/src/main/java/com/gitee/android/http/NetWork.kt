package com.gitee.android.http

import retrofit2.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Title: NetWork
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/8/13  16:47
 */
class NetWork {

    private val giteeService = ServiceCreator.create(ApiService::class.java)

//    suspend fun getWxArticleTabs(): BaseResponse<List<WxArticleTabsEntity>>? =
//        giteeService.getWxArticleTabs().await()
//
//    suspend fun getWxArticleDetail(
//        chapterId: String,
//        pageNumber: Int
//    ): BaseResponse<BasePage<List<Article>?>?>? =
//        giteeService.getWxArticleDetail(chapterId, pageNumber).await()

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
        private var network: NetWork? = null
        fun getInstance(): NetWork {
            if (network == null) {
                synchronized(NetWork::class.java) {
                    if (network == null) {
                        network = NetWork()
                    }
                }
            }
            return network!!
        }
    }

}