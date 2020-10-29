package com.gitee.android.http

import com.gitee.android.common.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Title: ApiFactory
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  14:36
 */
object ApiFactory {

    inline fun <reified T> create(
        baseUrl: String,
        saveCookie: Boolean,
        noinline creator: (Int, String, Any?) -> Any
    ): T {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder().addInterceptor(logger)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())

            //todo 2020年10月29日 16:47:16  移除 Call 包装
            .addCallAdapterFactory(LiveDataCallAdapterFactory(creator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

}