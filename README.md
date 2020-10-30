# 

> novoda 目前不支持 Gradle 6+ , 替换方案 `https://github.com/panpf/bintray-publish`

novoda

```
https://github.com/novoda/bintray-release/wiki/%E4%B8%AD%E6%96%87%E6%96%87%E6%A1%A3HOME

apply plugin: 'com.novoda.bintray-release'

gradlew clean build bintrayUpload -PbintrayUser=javakam -PbintrayKey=xxx -PdryRun=false


```
panpf

```
https://github.com/panpf/bintray-publish
gradlew clean build bintrayUpload -PbintrayUser=BINTRAY_USERNAME -PbintrayKey=BINTRAY_KEY -PdryRun=false
apply plugin: 'com.github.panpf.bintray-publish'
```

## 网络框架(Retrofit & LiveData)
LiveData Adapter for Retrofit

https://gist.github.com/AkshayChordiya/15cfe7ca1842d6b959e77c04a073a98f

https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample


## MVVM + Hilt
https://itnext.io/android-architecture-hilt-mvvm-kotlin-coroutines-live-data-room-and-retrofit-ft-8b746cab4a06

https://github.com/sdwfqin/AndroidQuick/tree/4.x/app-kt


## 混淆


## 单例
```kotlin
companion object {
    private var usbUtils: UsbUtils? = null
    fun getInstance(): UsbUtils {
        if (usbUtils == null) {
            synchronized(UsbUtils::class.java) {
                if (usbUtils == null) {
                    usbUtils = UsbUtils()
                }
            }
        }
        return usbUtils!!
    }
}
```


## DataBinding 在XML中的具体使用方式
```

<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    android:id="@+id/swipeRefresh"
    bind:colorSchemeResources="@{resId}"
    bind:onRefreshListener="@{() -> viewModel.onRefresh()}"
    bind:refreshing="@{viewModel.refreshing}"
    android:layout_width="match_parent"
    android:layout_height="match_parent"></androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

<TextView
    android:id="@+id/tv_article_tabs"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text='@{"复杂的表达式显示结果  "+@string/app_name+" -> "  + viewModel.wxArticleTabs.size()}' />

<ImageView
    android:id="@+id/iv_article_tabs_bind"
    loadPic="@{viewModel.tempImageUrl}"
    android:layout_width="35dp"
    android:layout_height="35dp"
    android:layout_marginTop="3dp"
    android:scaleType="centerCrop" />

```

## 添加矢量图SVG
https://developer.android.com/studio/write/vector-asset-studio?hl=zh-cn

## 参考项目

https://github.com/k3marek/GithubBrowser

https://github.com/omjoonkim/GitHubBrowserApp

https://github.com/zyyoona7/KExtensions/blob/master/lib/src/main/java/com/zyyoona7/extensions/

https://github.com/shiweibsw/Android-kotlin-extend-utils/blob/master/app/src/main/java/com/kd/kotlin/extend/utils/

## Tips

1. NavController,BottomNavigationView 不同步问题

https://medium.com/@freedom.chuks7/how-to-use-jet-pack-components-bottomnavigationview-with-navigation-ui-19fb120e3fb9

2. xml中 fragment -> FragmentContainerView 异常

https://stackoverflow.com/questions/58320487/using-fragmentcontainerview-with-navigation-component

```
error:
navController = Navigation.findNavController(this, R.id.nav_host)

success:
val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
navController = navHostFragment.navController
```

3. Retrofit 姿势错误
```
 URL query string "{page}" must not have replace block. For dynamic query parameters use @Query.
     for method ApiService.getRecommendProjects
```

4. Glide AppGlideModule
```
Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
```

5. LiveData() with no args
```
Failed to invoke public androidx.lifecycle.LiveData() with no args
```

🍎 LiveData + Retrofit
https://github.com/pivincii/livedata_retrofit

https://www.ericdecanini.com/2019/11/11/3-ways-to-use-retrofit-with-livedata-in-the-mvvm-android-architecture/

https://github.com/square/retrofit/issues/3075

https://medium.com/@pivincii/using-retrofit-with-livedata-5c5a49544ba3

