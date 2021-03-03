package com.gitee.android.common

import ando.toolkit.ACache
import com.gitee.android.bean.LoginEntity
import com.gitee.android.bean.UserInfoEntity

/**
 * Title:CacheManager
 * <p>
 * Description: 1.用户登录信息 ; 2. 用户详细信息
 * </p>
 * @author javakam
 * @date 2020/11/4  10:51
 */
object CacheManager {

    private val aCache: ACache? by lazy { ACache.build(AC_NAME) }
    private const val KEY_LOGIN = "login"
    private const val KEY_USER = "user"

    fun saveLoginData(loginEntity: LoginEntity?) {
        loginEntity?.also {
            aCache?.remove(KEY_LOGIN)
            aCache?.put(KEY_LOGIN, it)
        }
    }

    fun getLoginData(): LoginEntity? {
        return aCache?.getAsObject(KEY_LOGIN) as? LoginEntity?
    }

    fun getAccessToken(): String? = getLoginData()?.access_token

    fun haveLoginTrace(): Boolean = (aCache?.getAsObject(KEY_LOGIN) != null)

    ////////////////////////////////////

    fun saveUserInfo(userInfo: UserInfoEntity?) {
        userInfo?.also {
            aCache?.remove(KEY_USER)
            aCache?.put(KEY_USER, it)
        }
    }

    fun getUserInfo(): UserInfoEntity? {
        return aCache?.getAsObject(KEY_USER) as? UserInfoEntity?
    }

    fun getUserName(): String? = getUserInfo()?.name

}