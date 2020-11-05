package com.gitee.android.bean

import com.google.gson.annotations.SerializedName
import org.greenrobot.greendao.annotation.Entity
import java.io.Serializable

@Entity
data class LoginEntity(
    val access_token: String,
    val created_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val token_type: String
) : Serializable

data class UserInfoEntity(
    val avatar_url: String,
    val bio: String,
    val blog: String,
    val created_at: String,
    val email: String,
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val name: String,
    val organizations_url: String,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val stared: Int,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val updated_at: String,
    val url: String,
    val watched: Int,
    val weibo: Any
): Serializable

@Entity
data class ArticleEntity(
    val created_at: String,
    val default_branch: String,
    val description: String,
    @SerializedName(value = "fork?")
    val fork: Boolean,
    val forks_count: Int,
    val id: Int,
    val issues_enabled: Boolean,
    val language: String,
    val last_push_at: String,
    val name: String,
    val name_with_namespace: String,
    val namespace: Namespace,
    val owner: Owner,
    val paas: Any,
    val parent_id: Int,
    val parent_path_with_namespace: Any,
    val path: String,
    val path_with_namespace: String,
    val `public`: Boolean,
    val pull_requests_enabled: Boolean,
    val recomm: Int,
    val relation: Any,
    val stared: Any,
    val stars_count: Int,
    val watched: Any,
    val watches_count: Int,
    val wiki_enabled: Boolean
)

data class Namespace(
    val address: String,
    val avatar: String,
    val created_at: String,
    val description: String,
    val email: String,
    val enterprise_id: Int,
    val from: Any,
    val id: Int,
    val level: Int,
    val location: String,
    val name: String,
    val outsourced: Boolean,
    val owner_id: Int,
    val path: String,
    val `public`: Boolean,
    val updated_at: String,
    val url: String
)

data class Owner(
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val new_portrait: String,
    val portrait_url: String,
    val state: String,
    val username: String
)

//import androidx.room.*
//import com.google.gson.annotations.SerializedName
//
///**
// * Title: HttpEntity
// * <p>
// * Description:
// * </p>
// * @author changbao
// * @date 2020/9/24  16:49
// */
//
//data class BaseResponse<T>(
//    @SerializedName("data") val data: T?,
//    @SerializedName("errorCode") val errorCode: Int? = 0,
//    @SerializedName("errorMsg") val errorMsg: String? = ""
//)
//
//@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
//@Entity(tableName = "t_article_tabs")
//data class WxArticleTabsEntity(
//    @ColumnInfo(name = "tabId") @PrimaryKey @SerializedName("id") val id: Int?,
//    @ColumnInfo(name = "tabName") @SerializedName("name") val name: String?,
//
//    @SerializedName("order") val order: Int?,
//    @SerializedName("courseId") val courseId: Int?,
//    @SerializedName("parentChapterId") val parentChapterId: Int?,
//    @SerializedName("userControlSetTop") val userControlSetTop: Boolean? = false,
//    @SerializedName("visible") val visible: Int? = 1
//) {
//    @Ignore
//    @SerializedName("children")
//    val children: List<Any>? = null
//}
