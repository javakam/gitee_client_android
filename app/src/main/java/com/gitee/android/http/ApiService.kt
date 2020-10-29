package com.gitee.android.http

import androidx.lifecycle.LiveData
import com.ando.toolkit.log.L
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.bean.Page
import com.gitee.android.common.BASE_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Title: ApiService
 * <p>
 * Description:
 * </p>
 * @author changbao
 * @date 2020/8/13  16:05
 */
interface ApiService {

    companion object {
        fun get(): ApiService {
            return ApiFactory.create(
                BASE_URL,
                true
            ) { code, msg, obj ->
                L.i("code=$code  $msg  $obj")
               Response("")
            }
        }
    }

    @GET("v3/projects/featured/")
    fun getRecommendProjects(@Query("page") page: Int = 1): Call<LiveData<Page<ArticleEntity>>>

    @GET("v3/projects/popular/")
    fun getHotProjects(@Query("page") page: Int = 1): Call<LiveData<Page<ArticleEntity>>>

    @GET("v3/projects/latest/")
    fun getRecentlyProjects(@Query("page") page: Int = 1): Call<LiveData<Page<ArticleEntity>>>

}