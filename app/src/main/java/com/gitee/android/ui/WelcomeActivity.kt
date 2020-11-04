package com.gitee.android.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.ando.library.base.BaseActivity
import com.gitee.android.R

/**
 * 欢迎界面
 *
 * @author ChangBao
 */
class WelcomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View(this)
        view.setBackgroundResource(R.mipmap.ic_gitee_start)
        setContentView(view)

        //渐变展示启动屏
        val aa = AlphaAnimation(0.3f, 1.0f)
        aa.duration = 2000
        view.startAnimation(aa)
        aa.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
    }
}