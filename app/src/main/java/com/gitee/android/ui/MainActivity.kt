package com.gitee.android.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.children
import ando.library.base.BaseMvvmActivity
import ando.toolkit.BottomNavUtils
import ando.toolkit.NoShakeClickListener2
import com.gitee.android.R
import com.gitee.android.common.AppRouter
import com.gitee.android.common.CacheManager
import com.gitee.android.common.isLogin
import com.gitee.android.databinding.ActivityMainBinding
import com.gitee.android.http.GiteeRepoRemote
import com.gitee.android.ui.home.HomeFragment
import com.gitee.android.ui.repo.RepoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseMvvmActivity<ActivityMainBinding>() {

    @Inject
    lateinit var repo: GiteeRepoRemote

    private lateinit var navView: BottomNavigationView
    private lateinit var navViewController: BottomNavUtils

    override val layoutId: Int = R.layout.activity_main

    //问题: MainActivity使用的启动模式是SingleTask，我将闪屏页去掉后，无论打开多少页面，将应用推至后台再启动就回到了主页（MainActivity）
    //郭霖公众号: https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650253197&idx=1&sn=e9986456f709f00fb2d36940e1c18b30
    override fun onCreate(savedInstanceState: Bundle?) {

        if (!this.isTaskRoot) { // 当前类不是该Task的根部，那么之前启动
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) { // 当前类是从桌面启动的
                    // finish掉该类，直接打开该Task中现存的Activity
                    finish()
                    return
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        prepareLogin()

        navView = findViewById(R.id.bottomNavView)
        navViewController = BottomNavUtils(containerId = R.id.main_container, size = 3)
        navViewController.attach(navView, supportFragmentManager)
        navViewController.putFragments(
            R.id.nav_home to HomeFragment(),
            R.id.nav_repo to RepoFragment(),
            R.id.nav_mine to MineFragment()
        )

        //屏蔽长按吐司
        (navView.getChildAt(0) as? ViewGroup)?.children?.forEach { it.setOnLongClickListener { true } }

        //快速点击事件
        val fastClick = object : NoShakeClickListener2() {
            override fun onFastClick(item: Any?) {
                super.onFastClick(item)
                (item as? MenuItem?)?.apply {
                    //val fg = fragmentArray.get(itemId)
                    //if (fg.isAdded) fg.refreshData()
                }
            }
        }

        navView.setOnNavigationItemSelectedListener {
            navViewController.switchPage(it.itemId)
            fastClick.proceedClick(it)
            true
        }
    }

    private fun prepareLogin() {
        repo.loginByToken()?.observe(this) { r ->
            if (r?.isSuccessful == true) {
                r.body?.apply {
                    CacheManager.saveLoginData(this)
                    getUserInfo(this.access_token)
                    isLogin = true
                    navViewController.switchDefaultPage(R.id.nav_home)
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

}