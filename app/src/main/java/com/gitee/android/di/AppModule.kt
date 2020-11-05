package com.gitee.android.di

import com.ando.toolkit.AppUtils
import com.ando.toolkit.log.L
import com.gitee.android.common.BASE_URL
import com.gitee.android.http.ApiService
import com.gitee.android.http.GiteeRepoRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGiteeRepoRemote(): GiteeRepoRemote = GiteeRepoRemote.get()


//    @Provides
//    fun provideWeatherService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttp)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//    }
//
//    @Provides
//    fun provideOkHttpClient(): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//        if (AppUtils.isDebug()) {
//            builder.addInterceptor(HttpLoggingInterceptor { message ->
//                val strLength: Int = message.length
//                var start = 0
//                var end = 2000
//                for (i in 0..99) {
//                    if (strLength > end) {
//                        L.d("123", message.substring(start, end))
//                        start = end
//                        end += 2000
//                    } else {
//                        L.d("123", message.substring(start, strLength))
//                        break
//                    }
//                }
//            }.setLevel(HttpLoggingInterceptor.Level.BODY))
//        }
//        return builder.build()
//    }
}