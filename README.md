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