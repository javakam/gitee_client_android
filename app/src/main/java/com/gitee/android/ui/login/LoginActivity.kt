package com.gitee.android.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import com.ando.library.base.BaseMvvmActivity
import com.ando.toolkit.ext.afterTextChanged
import com.ando.toolkit.ext.hideSoftInput

import com.gitee.android.R
import com.gitee.android.databinding.ActivityLoginBinding

class LoginActivity : BaseMvvmActivity<ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override val layoutId: Int = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

        val username = findViewById<EditText>(R.id.et_account)
        val password = findViewById<EditText>(R.id.et_password)
        val login = findViewById<Button>(R.id.bt_login)

        username.afterTextChanged {

        }

        password.apply {
            afterTextChanged {

            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

        }

        login.setOnClickListener {
            hideSoftInput(this)
            loginViewModel.checkLogin(username.text.toString(), password.text.toString())
        }

    }
}

