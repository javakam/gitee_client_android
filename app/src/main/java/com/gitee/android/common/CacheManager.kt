package com.gitee.android.common

import com.ando.toolkit.ACache
import com.gitee.android.bean.LoginEntity

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

    fun saveLoginData(loginEntity: LoginEntity) {
        aCache?.remove(KEY_LOGIN)
        aCache?.put(KEY_LOGIN, loginEntity)
    }

    fun getLoginData(): LoginEntity? {
        return aCache?.getAsObject(KEY_LOGIN) as? LoginEntity?
    }

    fun haveLoginTrace(): Boolean = (aCache?.getAsObject(KEY_LOGIN) != null)

}