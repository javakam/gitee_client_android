package com.gitee.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.ando.library.base.BaseFragment
import com.gitee.android.R
import com.gitee.android.common.switchFragment
import com.gitee.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment).navController
        binding.apply {
            bottomNavView.setupWithNavController(navController)
            bottomNavView.selectedItemId = R.id.nav_home
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}