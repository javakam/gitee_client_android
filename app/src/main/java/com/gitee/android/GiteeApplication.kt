package com.gitee.android

import android.annotation.SuppressLint
import android.app.Application

/**
 * Title: GiteeApplication
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/9/24  16:44
 */
class GiteeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Application
    }

}