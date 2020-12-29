package com.gitee.android.http

import androidx.lifecycle.LiveData
import com.gitee.android.bean.ArticleEntity
import com.gitee.android.bean.LoginEntity
import com.gitee.android.bean.RepoEntity
import com.gitee.android.bean.UserInfoEntity
import com.gitee.android.common.BASE_URL
import com.gitee.android.common.BASE_URL_V3
import retrofit2.http.*

/**
 * Title: ApiService
 * <p>
 * Description:
 * </p>
 * @author javakam
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

    //https://gitee.com/api/v5/users/javakam/repos
    //?access_token=6a482b3d429b7646622468f566bf5d01&type=all&sort=full_name&page=1&per_page=20
    @GET("api/v5/users/{name}/repos?type=all&sort=full_name&per_page=20")
    fun getRepositories(
        @Path("name") name:String,
        @Query("access_token") access_token: String,
        @Query("page") page: Int = 1
    ): LiveData<ApiResponse<List<RepoEntity>?>?>

    @GET("projects/featured/")
    fun getRecommendProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>?>?>

    @GET("projects/popular/")
    fun getHotProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>?>?>

    @GET("projects/latest/")
    fun getRecentlyProjects(@Query("page") page: Int = 1): LiveData<ApiResponse<List<ArticleEntity>?>?>

}