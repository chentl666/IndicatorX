# IndicatorX
kotlin+mvvm+LiveData+viewModel+DataBinding+Navigator

自定义viewPager指示器

1.先在项目根目录的 build.gradle 的 repositories 添加:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2.然后在dependencies添加:
```
dependencies {
	...
	implementation 'com.github.chentl666:IndicatorX:1.0.1'
}
```

## 混淆配置
```sh
-keep class com.ctl.indicator.lib.** { *; }
```
