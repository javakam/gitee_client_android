package com.gitee.android.http

import androidx.lifecycle.LiveData
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.bean.LoginEntity
import com.gitee.android.bean.UserInfoEntity
import com.gitee.android.common.BASE_URL
import com.gitee.android.common.BASE_URL_V3
import retrofit2.http.*

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
        fun getV3(): ApiService = ApiFactory.create(BASE_URL_V3)
    }

    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@FieldMap fields: Map<String, String>): LiveData<ApiResponse<LoginEntity>?>?

    @FormUrlEncoded
    @POST("oauth/token")
    fun refreshToken(@FieldMap fields: Map<String, String>): LiveData<ApiResponse<LoginEntity>?>?

    @GET("api/v5/user")
    fun getUserInfo(@Query("access_token") access_token: String): LiveData<ApiResponse<UserInfoEntity?>?>?

    @GET("projects/featured/")
    fun getRecommendProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>>?>

    @GET("projects/popular/")
    fun getHotProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>>?>

    @GET("projects/latest/")
    fun getRecentlyProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>>?>

}