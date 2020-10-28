package com.gitee.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gitee.android.http.GiteeRepo
import com.gitee.android.ui.home.HomeViewModel

class ViewModelFactory (private val repo: GiteeRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}