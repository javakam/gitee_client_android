package ando.library.base

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Process.killProcess
import android.os.Process.myPid
import androidx.multidex.MultiDexApplication
import ando.library.BuildConfig
import ando.toolkit.ActivityCollector.add
import ando.toolkit.ActivityCollector.finishAll
import ando.toolkit.ActivityCollector.getAll
import ando.toolkit.ActivityCollector.remove
import ando.toolkit.ActivityCollector.setCurActivity
import ando.toolkit.AppUtils
import java.lang.ref.WeakReference

/**
 * # BaseApplication
 *
 * @author javakam
 * @date 2019/8/6  9:13
 */
open class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this, BuildConfig.DEBUG)

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
            //createConfigurationContext(configuration)
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
            killProcess(myPid())
            //System.exit(0)
        }

        /**
         * 触发 Home 事件
         *
         * 模拟用户退出到桌面, 并没有真正退出应用
         */
        fun exitHome(activity: Activity) {
            val backHome = Intent(Intent.ACTION_MAIN)
            backHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            backHome.addCategory(Intent.CATEGORY_HOME)
            activity.startActivity(backHome)
        }
    }

}