package com.gitee.android.ui.login

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import ando.library.base.BaseMvvmActivity
import ando.toolkit.ext.afterTextChanged
import ando.toolkit.ext.hideSoftInput
import ando.toolkit.ext.toastShort
import ando.toolkit.ext.yes

import com.gitee.android.R
import com.gitee.android.common.AppRouter
import com.gitee.android.common.CacheManager
import com.gitee.android.common.isLogin
import com.gitee.android.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseMvvmActivity<ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override val layoutId: Int = R.layout.activity_login

    override fun initActivityStyle() {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun initView(savedInstanceState: Bundle?) {

        val username = findViewById<EditText>(R.id.et_account)
        val password = findViewById<EditText>(R.id.et_password)
        val login = findViewById<Button>(R.id.bt_login)

        setTitle(R.string.login_title)

        username.afterTextChanged {
            if (!password.text.toString().isBlank() && !it.isBlank()) {
                login.isEnabled = true
            }
        }
        password.afterTextChanged {
            if (!username.text.toString().isBlank() && !it.isBlank()) {
                login.isEnabled = true
            }
        }

        login.setOnClickListener {
            hideSoftInput(this)
            val account = username.text.toString()
            val pwd = password.text.toString()

            loginViewModel.checkLogin(account, pwd).yes {
                loginViewModel.login(account, pwd)?.observe(this) {
                    it?.apply {
                        if (isSuccessful) {
                            CacheManager.saveLoginData(body)
                            getUserInfo(body?.access_token)
                            toastShort("登录成功")
                            isLogin = true
                            AppRouter.toMain(this@LoginActivity)
                        } else {
                            toastShort("登录失败")
                        }
                    }
                }
            }
        }
    }

    private fun getUserInfo(access_token: String?) {
        if (!access_token.isNullOrBlank()) {
            loginViewModel.getUserInfo(access_token)?.observe(this) {
                it?.apply {
                    if (isSuccessful) {
                        CacheManager.saveUserInfo(body)
                    }
                }
            }
        }
    }

}