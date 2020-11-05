package com.gitee.android.common

import com.ando.toolkit.ACache
import com.gitee.android.bean.LoginEntity
import com.gitee.android.bean.UserInfoEntity

/**
 * Title:
 * <p>
 * Description:
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

    fun haveLoginTrace(): Boolean = (aCache?.getAsObject(KEY_LOGIN) != null)

    fun saveUserInfo(userInfo: UserInfoEntity?){
        userInfo?.also {
            aCache?.remove(KEY_USER)
            aCache?.put(KEY_USER,it)
        }
    }

    fun getUserInfo(): UserInfoEntity? {
        return aCache?.getAsObject(KEY_USER) as? UserInfoEntity?
    }

}