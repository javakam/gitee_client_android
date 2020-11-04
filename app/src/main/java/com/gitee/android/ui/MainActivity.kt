package com.gitee.android.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ando.library.base.BaseMvvmActivity
import com.ando.toolkit.log.L
import com.gitee.android.R
import com.gitee.android.common.CacheManager
import com.gitee.android.databinding.ActivityMainBinding
import com.gitee.android.http.GiteeRepoRemote
import com.gitee.android.utils.FixFragmentNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseMvvmActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun initActivityStyle() {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        prepareLogin()
       // prepareLogin("javakam", "lovekam12")
    }

    override val layoutId: Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = fragment.navController

        //BottomNavigationView show/hide 而不是 replace
        val fragmentNavigator =
            FixFragmentNavigator(this@MainActivity, supportFragmentManager, fragment.id)
        navController.navigatorProvider.addNavigator(fragmentNavigator)
        navController.setGraph(R.navigation.nav_main_graph)

        binding.apply {
            bottomNavView.setupWithNavController(navController)
            //bottomNavView.selectedItemId = R.id.nav_home
        }
    }

    fun prepareLogin() {
        val data = GiteeRepoRemote.get().loginByToken()
        data?.observe(this) { r ->

            L.i("testLogin!  ${r?.toString()}")

            if (r?.isSuccessful == true) {
                L.i("登录成功!  $r")
                r.body?.apply { CacheManager.saveLoginData(this) }
            } else {
                //Login Page

            }

        }

    }

    fun prepareLogin(account: String, password: String) {
        val data = GiteeRepoRemote.get().login(account, password)
        data?.observe(this) { r ->

            L.i("testLogin!  ${r?.toString()}")

            if (r?.isSuccessful == true) {
                L.i("登录成功!  $r")
                r.body?.apply { CacheManager.saveLoginData(this) }
            }

        }

    }


    override fun onSupportNavigateUp() = navController.navigateUp()
}