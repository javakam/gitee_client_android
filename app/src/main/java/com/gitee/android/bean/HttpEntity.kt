package com.gitee.android.bean

//import androidx.room.*
//import com.google.gson.annotations.SerializedName
//
///**
// * Title: HttpEntity
// * <p>
// * Description:
// * </p>
// * @author javakam
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
//
//data class BasePage<T>(
//    @SerializedName("datas") val datas: T?,
//    @SerializedName("curPage") val curPage: Int? = 1,
//    @SerializedName("offset") val offset: Int? = 0,
//    @SerializedName("over") val over: Boolean = false,
//    @SerializedName("pageCount") val pageCount: Int? = 0,
//    @SerializedName("size") val size: Int? = 20,
//    @SerializedName("total") val total: Int? = 0
//)
//
//data class Article (
//    @SerializedName("apkLink") val apkLink: String,
//    @SerializedName("audit") val audit: Int,
//    @SerializedName("author") val author: String,
//    @SerializedName("canEdit") val canEdit: Boolean,
//    @SerializedName("chapterId") val chapterId: Int,
//    @SerializedName("chapterName") val chapterName: String,
//    @SerializedName("collect") val collect: Boolean,
//    @SerializedName("courseId") val courseId: Int,
//    @SerializedName("desc") val desc: String,
//    @SerializedName("descMd") val descMd: String,
//    @SerializedName("envelopePic") val envelopePic: String,
//    @SerializedName("fresh") val fresh: Boolean,
//    @SerializedName("id") val id: Int,
//    @SerializedName("link") val link: String,
//    @SerializedName("niceDate") val niceDate: String,
//    @SerializedName("niceShareDate") val niceShareDate: String,
//    @SerializedName("origin") val origin: String,
//    @SerializedName("prefix") val prefix: String,
//    @SerializedName("projectLink") val projectLink: String,
//    @SerializedName("publishTime") val publishTime: Long,
//    @SerializedName("realSuperChapterId") val realSuperChapterId: Int,
//    @SerializedName("selfVisible") val selfVisible: Int,
//    @SerializedName("shareDate") val shareDate: Long,
//    @SerializedName("shareUser") val shareUser: String,
//    @SerializedName("superChapterId") val superChapterId: Int,
//    @SerializedName("superChapterName") val superChapterName: String,
//    @SerializedName("tags") val tags: List<Tag>,
//    @SerializedName("title") val title: String,
//    @SerializedName("type") val type: Int,
//    @SerializedName("userId") val userId: Int,
//    @SerializedName("visible") val visible: Int,
//    @SerializedName("zan") val zan: Int,
//    @SerializedName("top") var top: String
//)
//
//data class Tag(
//    @SerializedName("name") val name: String,
//    @SerializedName("url") val url: String
//)
