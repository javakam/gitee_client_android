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

## 参考项目

https://github.com/zyyoona7/KExtensions/blob/master/lib/src/main/java/com/zyyoona7/extensions/


https://github.com/shiweibsw/Android-kotlin-extend-utils/blob/master/app/src/main/java/com/kd/kotlin/extend/utils/