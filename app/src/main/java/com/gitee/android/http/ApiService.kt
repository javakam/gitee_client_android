package com.gitee.android.http

import androidx.lifecycle.LiveData
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.common.BASE_URL
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
        fun get(): ApiService = ApiFactory.create(BASE_URL)
    }

    @GET("v3/projects/featured/")
    fun getRecommendProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>>?>

    @GET("v3/projects/popular/")
    fun getHotProjects(@Query("page") page: Int = 1): LiveData<List<ArticleEntity>?>

    @GET("v3/projects/latest/")
    fun getRecentlyProjects(@Query("page") page: Int = 1): LiveData<List<ArticleEntity>?>

}