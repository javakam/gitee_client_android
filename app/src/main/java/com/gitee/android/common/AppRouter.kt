package com.gitee.android.common

import android.app.Activity
import android.content.Intent
import com.gitee.android.ui.MainActivity
import com.gitee.android.ui.login.LoginActivity

/**
 * Title: AppRouter
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/11/5  14:52
 */
object AppRouter {

    fun toLogin(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    fun toMain(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }


}