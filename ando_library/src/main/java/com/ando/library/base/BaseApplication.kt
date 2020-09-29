package com.ando.library.base

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.ando.library.BuildConfig
import com.ando.toolkit.ActivityCollector.add
import com.ando.toolkit.ActivityCollector.finishAll
import com.ando.toolkit.ActivityCollector.getAll
import com.ando.toolkit.ActivityCollector.remove
import com.ando.toolkit.ActivityCollector.setCurActivity
import com.ando.toolkit.ToolKit
import java.lang.ref.WeakReference

/**
 * Title: BaseApplication
 *
 *
 * Description:
 *
 *
 * @author javakam
 * @date 2019/8/6  9:13
 */
open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ToolKit.init(this, BuildConfig.DEBUG)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(
                activity: Activity,
                savedInstanceState: Bundle?
            ) {
                add(WeakReference(activity))
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {
                setCurActivity(activity)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(
                activity: Activity,
                outState: Bundle
            ) {
                getAll()
            }

            override fun onActivityDestroyed(activity: Activity) {
                remove(WeakReference(activity))
            }
        })
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     *
     * https://www.jianshu.com/p/5effff3db399
     */
    override fun getResources(): Resources? { //禁止app字体大小跟随系统字体大小调节
        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f
            resources.updateConfiguration(configuration, resources.displayMetrics)
//            createConfigurationContext(configuration)
        }
        return resources
    }

    companion object {
        /**
         * 是否灰度显示
         */
        var isGray = false

        fun exit() {
            finishAll()
            //Process.killProcess(Process.myPid())
            System.exit(0)
        }
    }

}