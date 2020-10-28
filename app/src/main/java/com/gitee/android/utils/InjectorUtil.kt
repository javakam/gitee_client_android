package com.gitee.android.utils

import com.gitee.android.http.GiteeRepo
import com.gitee.android.http.NetWork
import com.gitee.android.viewmodel.ViewModelFactory

object InjectorUtil {

//    private val appDao by lazy {
//        AppDatabase.getDatabase(WanAndroidApplication.instance, null).wanAndroidDao()
//    }

    private fun getRepo() = GiteeRepo.getInstance(NetWork.getInstance())

    fun getViewModelFactory() = ViewModelFactory(getRepo())

}