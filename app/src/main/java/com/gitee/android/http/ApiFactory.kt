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

    /**
     *  saveCookie: Boolean, noinline creator: (Int, String, Any?) -> Any
     */
    inline fun <reified T> create(baseUrl: String): T {
        val logger = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder().addInterceptor(logger)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory(creator))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

}