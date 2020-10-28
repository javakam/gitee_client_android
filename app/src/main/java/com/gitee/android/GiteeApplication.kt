package com.gitee.android

import ando.file.FileOperator
import ando.file.core.FileDirectory
import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ando.library.utils.CrashHandler
import com.ando.toolkit.ToolKit
import com.ando.toolkit.log.L
import com.gitee.android.common.VIEW_REFRESH_COLOR
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp

/**
 * Title: GiteeApplication
 * <p>
 * Description:
 * </p>
 * @author changbao
 * @date 2020/9/24  16:44
 */
@HiltAndroidApp
class GiteeApplication : Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        SmartRefreshLayout.setDefaultRefreshInitializer { _, l ->
            l.setEnableAutoLoadMore(true)
            l.setEnableOverScrollDrag(false)
            l.setEnableOverScrollBounce(false)
            l.setEnableLoadMoreWhenContentNotFull(false) //是否在列表不满一页时候开启上拉加载功能
            l.setEnableScrollContentWhenRefreshed(false)
            l.setPrimaryColorsId(android.R.color.transparent, R.color.font_black_light)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { c, _ ->
            MaterialHeader(c).setColorSchemeResources(VIEW_REFRESH_COLOR)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { c, _ ->
            ClassicsFooter(c)
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE=this

        val isDebug = BuildConfig.DEBUG
        ToolKit.init(this,isDebug)
        FileOperator.init(this, isDebug)
        CrashHandler.init(this, "${FileDirectory.getCacheDir().absolutePath}/Crash/")
        L.init("123", isDebug)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: Application
    }

}