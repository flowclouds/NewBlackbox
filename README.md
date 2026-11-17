# tm-box

<p align="center">
  <img src="assets/usage.gif" alt="tm-box" width="100%"/>
</p>

在 Android 上把应用跑在沙箱里。当前只编 arm64-v8a。引擎包名 `com.tm.box`，示例应用包名 `com.tm.box.sample`，安装包名 `com.tm.box`。

## Overview

This enhanced edition includes bug fixes, stability improvements, and Android 14+ compatibility tailored for modern devices.

### Key Features

*   **Virtual App Cloning**: Run multiple instances of applications.
*   **Sandboxed Environment**: Isolated process execution.
*   **No Root Required**: Runs entirely in userspace.
*   **Multi-Architecture**: arm64-v8a only.
*   **Device Spoofing**: Modify device information for virtual apps.
*   **Fake Location**: Spoof GPS coordinates.

## 代码分层

引擎打成 aar 之后，使用方只依赖 aar，不把 `Bcore` 源码引进去，也不改 `nativeHook()`。

引擎自带的挂钩在 `libtmbox.so` 里。沙箱进程启动时，`BActivityThread.handleBindApplication` 调 `IOCore.enableRedirect`，进到 `nativeHook()`，装上 `UnixFileSystemHook`、`BinderHook`、`DexFileHook`、`JniHook`。这些跟着 aar 走，使用方碰不到。

使用方自己的一套写在自己的模块里。沙箱进程（`:p0`）同样会执行宿主的 `Application.attachBaseContext`。在那里调用 `TmBox.addAppLifecycleCallback`。`beforeCreateApplication` 发生在访客 `makeApplication` 之前，应用自己的 so 还没加载。

```kotlin
TmBox.get().addAppLifecycleCallback(object : AppLifecycleCallback() {
    override fun beforeCreateApplication(
        packageName: String?, processName: String?, context: Context?, userId: Int
    ) {
        System.loadLibrary("myhook")
    }
})
```

这段要放在 `Application.attachBaseContext` 里，和 `doAttachBaseContext` 放在一起。只在主进程注册的话，沙箱进程里没有这个对象。

`libtmbox.so` 不导出挂钩函数。自己的 so 自己链接 Dobby，在 `JNI_OnLoad` 里挂。符号用 `xdl_open` + `xdl_sym`，找不到再 `xdl_dsym`。`DobbyHook` 返回 0 才算挂上，失败打日志并跳过。

流量。只看对端地址，本机和端口 0 丢掉：

```cpp
static int (*orig_connect)(int, const struct sockaddr *, socklen_t);

static int hook_connect(int fd, const struct sockaddr *addr, socklen_t len) {
    // 从 addr 取出 ip 和 port，打日志
    return orig_connect(fd, addr, len);
}

void traffic_install() {
    void *handle = xdl_open("libc.so", XDL_DEFAULT);
    void *target = handle ? xdl_sym(handle, "connect", nullptr) : nullptr;
    if (handle) xdl_close(handle);
    if (!target || DobbyHook(target, (void *) hook_connect, (void **) &orig_connect) != 0)
        ALOGE("traffic hook failed");
}
```

URL。`libflutter.so` 的 `SSL_write` 没有导出，要等这个库被加载。在 `JNI_OnLoad` 里挂钩 `libdl.so` 的 `android_dlopen_ext`，路径里出现 `libflutter.so` 再算地址。bKash 7.4.0 这份 so 的 sha256 前缀是 `7cc17422`，`JNI_OnLoad` 在文件偏移 `0x4960cc`，`SSL_write` 在 `0x717ef0`。先核对第一条指令是 `0xd10103ff`（`sub sp, #0x40`），对不上就不要挂。

```cpp
static void *hook_dlopen_ext(const char *filename, int flags, const void *info) {
    void *handle = orig_dlopen_ext(filename, flags, info);
    if (handle && filename && strstr(filename, "libflutter.so")) {
        void *flutter = xdl_open(filename, XDL_DEFAULT);
        void *jni = flutter ? xdl_sym(flutter, "JNI_OnLoad", nullptr) : nullptr;
        if (flutter) xdl_close(flutter);
        auto *site = (uint32_t *) ((uintptr_t) jni + (0x717ef0 - 0x4960cc));
        if (jni && *site == 0xd10103ff)
            DobbyHook(site, (void *) hook_ssl_write, (void **) &orig_ssl_write);
    }
    return handle;
}
```

`hook_ssl_write(ssl, buf, num)` 的 `buf` 是明文。请求行加 `Host` 头拼出 `https://host/path`。一次 `SSL_write` 没写完请求行时会漏。so 换版本后偏移失效，靠开头指令跳过。不要再挂钩 `dlopen`，之前那样会把进程打崩。

### Java

容器的 Java 入口也是上面的 `AppLifecycleCallback`。`beforeCreateApplication`、`beforeApplicationOnCreate`、`afterApplicationOnCreate` 由 `BActivityThread` 调用。活动恢复由容器的 `Instrumentation` 转发。这里不要写某个应用的功能。

自己的实现单独一个类。在 `beforeApplicationOnCreate` 里调用它，先看包名，再注册到这个访客的 `Application` 上。这样只有目标应用会走到你的回调。bKash 的 `Application` 是 `com.example.first.flutter.bkash.bkashnext.MainApplication`。

```kotlin
fun install(packageName: String?, application: Application?) {
    if (packageName != "com.bKash.customerapp" || application == null) return
    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity) {
            if (activity.javaClass.name != "com.bKash.customerapp.MainActivity") return
            AlertDialog.Builder(activity)
                .setTitle("bKash hook")
                .setMessage("挂在 ${application.javaClass.name}")
                .setPositiveButton("知道了", null)
                .show()
        }
        override fun onActivityCreated(a: Activity, s: Bundle?) {}
        override fun onActivityStarted(a: Activity) {}
        override fun onActivityPaused(a: Activity) {}
        override fun onActivityStopped(a: Activity) {}
        override fun onActivitySaveInstanceState(a: Activity, s: Bundle) {}
        override fun onActivityDestroyed(a: Activity) {}
    })
}
```

弹窗是使用方自己的 `AlertDialog`，画在访客主页上。同一个 `MainActivity` 会多次 `onResume`，用 `decorView` 的 tag 挡一下，避免重复弹出。

## Requirements

*   **Android Version**: Android 5.0 (API 21) or higher.
*   **RAM**: 2GB minimum recommended.
*   **Architecture**: ARM64-v8a.

## Build Instructions

### Prerequisites
*   Android Studio (Arctic Fox or newer)
*   JDK 17
*   Android SDK 34+
*   NDK (Version 29.0.13846066)

### Building from Source

```bash
# Clone the repository
git clone https://github.com/flowclouds/NewBlackbox.git
cd NewBlackbox

# Build Debug APK
./gradlew assembleDebug

# Build Release APK
./gradlew assembleRelease
```

## Integration

把打出来的 aar 放进自己的工程：

```gradle
dependencies {
    implementation fileTree(dir: "libs", include: ["*.aar"])
}
```

Refer to `Docs.md` for detailed API documentation.

## Troubleshooting

*   **App Crashes**: Check logcat for UID mismatches or permission errors.
*   **Installation Failures**: Verify potential architecture mismatches or storage permissions.
*   **Android 15**: Ensure you are using the latest build which handles stricter security policies.

## Credits

*   **Main Developer**: ALEX502
*   **Original Framework**: VirtualApp, VirtualAPK
*   **Native Hooks**: Dobby, xDL
*   **Reflection**: BlackReflection, FreeReflection

## License

Copyright 2022 BlackBox

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
