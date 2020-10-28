package com.gitee.android.http

import com.gitee.android.bean.ArticleEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("v3/projects/featured/")
    fun getRecommendProjects(@Query("page") page: Int = 1): Call<List<ArticleEntity>?>

    @GET("v3/projects/popular/")
    fun getHotProjects(@Query("page") page: Int = 1): Call<List<ArticleEntity>?>

    @GET("v3/projects/latest/")
    fun getRecentlyProjects(@Query("page") page: Int = 1): Call<List<ArticleEntity>?>

}