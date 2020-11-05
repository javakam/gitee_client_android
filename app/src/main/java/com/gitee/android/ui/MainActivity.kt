package com.gitee.android.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ando.library.base.BaseMvvmActivity
import com.gitee.android.R
import com.gitee.android.common.AppRouter
import com.gitee.android.common.CacheManager
import com.gitee.android.databinding.ActivityMainBinding
import com.gitee.android.http.GiteeRepoRemote
import com.gitee.android.utils.FixFragmentNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseMvvmActivity<ActivityMainBinding>() {

    @Inject
    lateinit var repo: GiteeRepoRemote

    private lateinit var navController: NavController

    override val layoutId: Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        prepareLogin()

        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = fragment.navController

        //BottomNavigationView show/hide 而不是 replace
//        val fragmentNavigator =
//            FixFragmentNavigator(this@MainActivity, supportFragmentManager, fragment.id)
//        navController.navigatorProvider.addNavigator(fragmentNavigator)
        navController.setGraph(R.navigation.nav_main_graph)

        binding.apply { bottomNavView.setupWithNavController(navController) }

    }

    private fun prepareLogin() {
        repo.loginByToken()?.observe(this) { r ->
            if (r?.isSuccessful == true) {
                r.body?.apply {
                    CacheManager.saveLoginData(this)
                    getUserInfo(this.access_token)
                }
            } else {
                AppRouter.toLogin(this)// Login Page
            }
        }
    }

    private fun getUserInfo(access_token: String) {
        repo.getUserInfo(access_token)?.observe(this) {
            it?.apply {
                if (isSuccessful) {
                    CacheManager.saveUserInfo(body)
                }
            }
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}