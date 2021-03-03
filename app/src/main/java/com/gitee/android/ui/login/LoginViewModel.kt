package com.gitee.android.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import ando.toolkit.ext.ToastUtils
import com.gitee.android.base.BaseViewModel
import com.gitee.android.bean.LoginEntity
import com.gitee.android.bean.UserInfoEntity
import com.gitee.android.http.ApiResponse
import com.gitee.android.http.GiteeRepoRemote

class LoginViewModel @ViewModelInject constructor(private val repo: GiteeRepoRemote) :
    BaseViewModel() {

    /**
     * 检查登录
     */
    fun checkLogin(username: String?, password: String?): Boolean {
        if (!isUserNameValid(username)) {
            ToastUtils.shortToast("请输入账号")
            return false
        }
        if (password?.isBlank() == true) {
            ToastUtils.shortToast("请输入密码")
            return false
        }
        if (!isPasswordValid(password ?: return false)) {
            ToastUtils.shortToast("密码长度至少6位")
            return false
        }
        return true
    }

    fun login(account: String, password: String): LiveData<ApiResponse<LoginEntity>?>? {
        return repo.login(account = account, password = password)
    }

    fun getUserInfo(access_token: String): LiveData<ApiResponse<UserInfoEntity?>?>? {
        return repo.getUserInfo(access_token = access_token)
    }

    private fun isUserNameValid(username: String?): Boolean {
        return (username?.isBlank() == false)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}