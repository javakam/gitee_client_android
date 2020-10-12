package com.gitee.android

import ando.file.FileOperator
import ando.file.core.FileDirectory
import ando.file.core.FileOpener
import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ando.library.utils.CrashHandler
import com.ando.toolkit.log.L
import com.gitee.android.view.DynamicTimeFormat
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * Title: GiteeApplication
 * <p>
 * Description:
 * </p>
 * @author changbao
 * @date 2020/9/24  16:44
 */
class GiteeApplication : Application() {

    init {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(false)
            layout.setEnableLoadMoreWhenContentNotFull(true) //是否在列表不满一页时候开启上拉加载功能
            layout.setEnableScrollContentWhenRefreshed(false)
            layout.setPrimaryColorsId(android.R.color.transparent, R.color.font_black_light)
            //
            //layout.setHeaderHeight(DensityUtils.dp2px(context, R.dimen.dp_50));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
            //layout.setFooterHeight(DensityUtils.dp2px(context, R.dimen.dp_50));//Footer标准高度（显示上拉高度>=标准高度 触发加载）
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
//            val header = MaterialHeader(context)
//            header.setColorSchemeResources(Constants.VIEW_REFRESH_COLOR)
//            header
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"));
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(
                context
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        val isDebug = true
        FileOperator.init(this, isDebug)
        CrashHandler.init(this, "${FileDirectory.getCacheDir().absolutePath}/Crash/")
        L.init("123", isDebug)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: Application
    }

}