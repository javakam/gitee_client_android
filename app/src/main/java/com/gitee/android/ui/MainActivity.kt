package com.gitee.android.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ando.library.base.BaseMvvmActivity
import com.gitee.android.R
import com.gitee.android.databinding.ActivityMainBinding
import com.gitee.android.utils.FixFragmentNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseMvvmActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override fun initActivityStyle() {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
            //NavGraphBuilder.build(this@MainActivity, navController, fragment.id)
            bottomNavView.setupWithNavController(navController)
            //bottomNavView.selectedItemId = R.id.nav_home
        }


    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}