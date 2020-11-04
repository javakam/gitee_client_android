package com.gitee.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ando.toolkit.ext.ToastUtils
import com.ando.toolkit.ext.postNext
import com.gitee.android.base.BaseViewModel
import com.gitee.android.bean.LoginEntity
import com.gitee.android.common.CacheManager
import com.gitee.android.http.ApiResponse
import com.gitee.android.http.GiteeRepoRemote

class LoginViewModel : BaseViewModel() {

    private val repo = GiteeRepoRemote.get()

    //todo  2020年11月4日 17:07:14
    val result = MutableLiveData<LoginEntity?>()

    /**
     * 检查登录
     */
    fun checkLogin(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            ToastUtils.shortToast("请输入账号")
            return
        }
        if (password?.isBlank() == true) {
            ToastUtils.shortToast("请输入密码")
            return
        }
        if (isPasswordValid(password ?: return)) {
            ToastUtils.shortToast("密码长度至少6位")
            return
        }
        login(username!!, password)
    }

    fun login(username: String, password: String) {
        val result: LiveData<ApiResponse<LoginEntity>?>? =
            repo.login(account = username, password = password)
        val a =   result?.apply {
            Transformations.map(this) {
                if (it?.isSuccessful == true) {
                    val data = it.body
                    CacheManager.saveLoginData(data ?: return@map isLogin.postValue(false) )
                    isLogin.postValue(true)
                    data
                } else {
                    isLogin.postValue(false)
                    null
                }
            }
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        return (username?.isBlank() == false)
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}