package com.gitee.android

import ando.file.FileOperator
import ando.file.core.FileDirectory
import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.ando.library.utils.CrashHandler
import com.ando.toolkit.AppUtils
import com.ando.toolkit.log.L
import com.gitee.android.common.VIEW_REFRESH_COLOR
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Title: GiteeApplication
 * <p>
 * Description:
 * </p>
 * @author javakam
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
        INSTANCE = this

        val isDebug = BuildConfig.DEBUG

        GlobalScope.launch {

            Realm.init(INSTANCE)
            // The RealmConfiguration is created using the builder pattern.
            // The Realm file will be located in Context.getFilesDir() with name "myrealm.realm"
            // Get the absolute path of a Realm by using Realm.getPath.
            val config = RealmConfiguration.Builder()
                .name("gitee.realm")
                //.readOnly()
                .schemaVersion(1)
//                .encryptionKey(getMyKey())
//                .modules(MySchemaModule())
//                .migration(MyMigration())
                .build()
            // Use the config
            sRealm = Realm.getInstance(config)

            Realm.setDefaultConfiguration(config)
        }
        AppUtils.init(this, isDebug)
        FileOperator.init(this, isDebug)
        CrashHandler.init(this, "${FileDirectory.getCacheDir().absolutePath}/Crash/")
        L.init("123", isDebug)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: Application
        lateinit var sRealm: Realm
    }

}