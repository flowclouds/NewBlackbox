# 混淆 SDK

Release 才会混淆。Debug 仍是原名，方便看日志。

## 命令

JDK 21。JDK 25 配不上 `:black-reflection`。

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
./gradlew :Bcore:assembleRelease
```

产物：`Bcore/build/outputs/aar/Bcore-release.aar`。

宿主如果要打进同一个仓库的 release 包：

```bash
./gradlew :app:assembleRelease
```

签名用的是 debug 证书，可以直接 `adb install -r -t -d`。

## 保留的名字

这些名字不能改，改了 JNI、反射或已经存下来的数据会对不上：

- `com.tm.box.TmBox` 的公开方法，以及 `BActivityThread`、`AppLifecycleCallback`、`ClientConfiguration`、`AbiUtils`
- `com.tm.box.core.NativeCore`、`com.tm.jni.jni.JniHook`（含 `NATIVE_OFFSET`）、`com.tm.jni.MethodUtils` 里 so 按名字找的方法和字段
- `com.tm.box.fake` 整包。挂钩类靠注解和 `newInstance()` 注册，R8 认不出这条引用
- `android.**` 和 `com.android.internal.**`。这些是系统 AIDL 的同名存根，改名后强转会失败
- `BoxManager` 的子类和 `core.system` 里的 AIDL 接口。服务是用泛型类名加上 `$Stub` 找出来的
- `com.tm.reflect` 和带 `@BClass` 的镜像。方法名就是系统类的字段名
- 实现了 `Parcelable` 的类名。沙箱里的安装记录按类名写在磁盘上
- `AndroidManifest` 里的 Activity、Service、Provider。系统按清单里的类名加载

其余引擎类由 R8 改名。规则在 `Bcore/proguard-rules.pro`，打进 aar 给宿主用的是同一份 `Bcore/consumer-rules.pro`。

## so

`libtmbox.so` 在 release 里：

- 只导出 `JNI_OnLoad`（`Bcore/src/main/cpp/export.map`）
- 去掉调试日志字符串（`SDK_RELEASE`）
- `-fvisibility=hidden` 加上 `--strip-all`。异常栈还留着，去掉之后 C++ 异常会直接中止进程

反编译仍能看到控制流。Dobby 是预编译的 `.a`，里面还留着它自己的源码路径。控制流平坦化要换编译器，这里没有加。

## 检查

```bash
unzip -l Bcore/build/outputs/aar/Bcore-release.aar | rg 'classes.jar|libtmbox'
# 映射里应能看到 TmBox 保留，内部类被改名
rg '^com.tm.box.fake' Bcore/build/outputs/mapping/release/mapping.txt | head
# 动态符号只剩 JNI_OnLoad
nm -D Bcore/build/intermediates/stripped_native_libs/release/stripReleaseDebugSymbols/out/lib/arm64-v8a/libtmbox.so
```
