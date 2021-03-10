package com.gitee.android.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val httpClient = OkHttpClient.Builder().addInterceptor(logger)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

}