# 混淆 SDK

Release 才混淆。Debug 仍是原名、原日志，方便看崩溃。

两件事是分开的：Java 用 R8 改名，native 用密文表藏字符串。控制流没有平坦化。进程起来之后，解开的字符串在内存里是明文。

## 命令

JDK 21。JDK 25 配不上 `:black-reflection`。

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
./gradlew :Bcore:assembleRelease
```

产物：`Bcore/build/outputs/aar/Bcore-release.aar`。

同一个仓库打宿主 release 包：

```bash
./gradlew :app:assembleRelease
```

签名用的是 debug 证书，可以直接 `adb install -r -t -d`。

宿主是工程依赖时，R8 吃的是未压缩的库 class，再加上 aar 里的 `consumer-rules.pro`。真正改名发生在宿主的 R8。所以 `Bcore/proguard-rules.pro` 和 `Bcore/consumer-rules.pro` 必须保持同一份保留名单。`app/proguard-rules.pro` 只指向这份名单。

## Java：什么必须留原名

规则在上面两个 proguard 文件里。改漏一个，release 能编过，装上才崩。

| 保留 | 原因 |
| --- | --- |
| `TmBox` 的公开方法，以及 `BActivityThread`、`AppLifecycleCallback`、`ClientConfiguration`、`AbiUtils` | 宿主直接调用 |
| `NativeCore` 的 native 方法和 `getCallingUid`、`redirectPath`、`loadEmptyDex` | so 按这个类名和方法名 `RegisterNatives` / `GetMethodID` |
| `JniHook` 的 native 方法，以及 `NATIVE_OFFSET`、`NATIVE_OFFSET_2` 两个 int | so 用 `GetStaticFieldID` 找。少一个字段，ART 会在第二次查找时中止 |
| `MethodUtils` 的 `getDeclaringClass`、`getMethodName`、`getDesc` | so 按名字找 |
| `com.tm.box.fake` 整包，方法名也留 | 挂钩类靠注解注册，而且要覆盖系统接口的原方法名。只留类名、允许方法改名，会在 `IServiceConnection.connected` 上抛 `AbstractMethodError` |
| `android.**`、`com.android.internal.**` | 系统 AIDL 的同名存根。改名后强转失败 |
| `BoxManager` 和八个 `B*Manager` 子类的类名 | `getGenericSuperclass()` 靠 Signature 取出服务接口，再拼 `$Stub`。类被改名后 Signature 丢了，强转会炸 |
| `core.system` 的接口、`$Stub`、`$Stub$Proxy` | 和上面同一套服务查找 |
| `BUserInfo` 的字段 | 宿主 `MainActivity` 读 `id`。字段被改名后是 `NoSuchFieldError` |
| `entity` 里的 `Parcelable`，以及所有 `Parcelable` 的类名和 `CREATOR` | 安装记录按类名写在磁盘上 |
| `com.tm.reflect` 和带 `@BClass` 的镜像 | 方法名就是系统类的字段名 |
| `AndroidManifest` 里的 Activity、Service、Provider | 系统按清单里的类名加载。`FileProvider` 的 `android:name` 不能改 |

其余引擎类由 R8 改名。不要把规则放宽成 `-keep class com.tm.box.**`。

## so：字符串为什么不能写在源码里

`-O2` 会把 C++ 字符串字面量留在 `.rodata`。写 `constexpr` 异或、再在 `noinline` 函数里解开，字面量还是在。所以源码里不能出现这些明文，调用点只能写数字 id。

表在 `Bcore/src/main/cpp/Obf.cpp`，接口在 `Obf.h`。

- `kEnc`：全部密文拼成一块
- `kOff[id]`、`kLen[id]`：第 id 条的起点和长度。现在 168 条，最长 116
- `kPlain[168][117]`：第一次 `obf_get` 时解开，之后一直留着
- 每个字节：`密文 ^ (0xC3 ^ ((下标 * 29) & 0xFF))`。下标是这条字符串内部的下标，不是 id
- `obf_apply` 标了 `noinline, optnone`，避免编译器把解开的结果再写回只读段
- `pthread_once` 保证只解一次

调用：

```cpp
#include "Obf.h"

auto cls = OBF(24);          // 要 .c_str() 时用这个
const char *name = OBF_C(9); // 直接要 const char* 时用这个
```

`OBF(24)` 是 `com/tm/box/core/NativeCore`，`OBF_C(9)` 是 `getCallingUid`。id 就是 `kOff` 的下标。要核对某一条，按上面的异或从 `kEnc + kOff[id]` 解 `kLen[id]` 个字节，不要把整张明文表写进仓库。

固定入口也占了 id，改表时要一起看：

| id | 谁在用 |
| --- | --- |
| 26 | `obf_maps()`，`/proc/self/maps` |
| 27 / 28 | `linker64` / `linker` |
| 29 / 30 | linker 的完整路径 |
| 31–35 | `dlsym` 要找的 linker 符号 |
| 36 / 37 | xdl 匹配用的 `/libc.so`、`/libart.so` |

xdl 的 C 文件不能在静态初始化里调用这些函数。`xdl_linker.c` 里 vendor 路径那两张表因此仍是明文，只在函数开头补 `/libc.so` 和 `/libart.so`。

## 加一条字符串

只往表尾追加。插到中间或整表重生成，后面所有 id 都会错，调用点不会跟着变。

1. 用上面的公式把新字符串逐字节加密。
2. 密文接到 `kEnc` 末尾。
3. `kOff` 追加旧表的总字节数，`kLen` 追加长度。
4. `kPlain` 的第一维、`obf_decode` 里的 `168` 都加 1。新字符串比 116 长时，第二维也要加大。
5. 调用点写新的 id。需要给 C 代码用时，再加一个 `extern "C"` 包装，像 `obf_maps` 那样。

## so：符号和日志

`libtmbox.so` 在 release 里还有这些：

- `export.map` 只把 `JNI_OnLoad` 放进动态符号，其余 `local`
- `Bcore/build.gradle` 的 release `ndkBuild` 传 `SDK_RELEASE=1`。`Log.h` 里 `ALOGD` / `ALOGE` 变成空
- `-fvisibility=hidden`、`-ffunction-sections`、`-fdata-sections`、`--gc-sections`、`--strip-all`
- 异常栈留着。加上 `-fno-unwind-tables`、`-fno-asynchronous-unwind-tables`、`-fomit-frame-pointer` 之后，C++ 异常会直接中止进程

## 反编译仍然看得到的

- 函数控制流。没有做控制流平坦化
- `obf_get` 之后的 `kPlain`
- Dobby 是预编译的 `.a`，里面有它自己的源码路径
- libc++abi / libunwind 的路径
- xdl 没进密文表的路径：`liblzma.so`、`/system/build.prop`、`app_process64`、`/system/lib64`、`/apex/com.android.vndk`，以及 vendor 的 linker 匹配串
- jadx 里的 `com.tm.box.fake` 类名和方法名。这是故意留的

## 检查

```bash
unzip -l Bcore/build/outputs/aar/Bcore-release.aar | rg 'classes.jar|libtmbox'

# 动态符号只剩 JNI_OnLoad
nm -D Bcore/build/intermediates/stripped_native_libs/release/stripReleaseDebugSymbols/out/lib/arm64-v8a/libtmbox.so

# .rodata 里不应再有这些明文
strings Bcore/build/intermediates/cxx/Release/*/obj/local/arm64-v8a/libtmbox.so \
  | rg 'NativeCore|UnixFileSystem|JniHook|openDexFileNative|/box/|linker64'
```
