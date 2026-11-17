# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.tm.box.TmBox { public *; }
-keep class com.tm.box.app.BActivityThread { public *; }
-keep class com.tm.box.app.configuration.AppLifecycleCallback { public *; }
-keep class com.tm.box.app.configuration.ClientConfiguration { public *; }
-keep class com.tm.box.utils.AbiUtils { public *; }
-keep class com.tm.box.entity.** implements android.os.Parcelable { *; }
-keepnames class * implements android.os.Parcelable
-keep class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.tm.box.core.NativeCore {
    public static native <methods>;
    public static int getCallingUid(int);
    public static java.lang.String redirectPath(java.lang.String);
    public static java.io.File redirectPath(java.io.File);
    public static long[] loadEmptyDex();
}
-keep class com.tm.jni.jni.JniHook {
    native <methods>;
    int NATIVE_OFFSET;
    int NATIVE_OFFSET_2;
}
-keep class com.tm.jni.MethodUtils {
    public static java.lang.String getDeclaringClass(java.lang.reflect.Method);
    public static java.lang.String getMethodName(java.lang.reflect.Method);
    public static java.lang.String getDesc(java.lang.reflect.Method);
}

-keep class com.tm.box.fake.frameworks.BoxManager
-keep class com.tm.box.fake.frameworks.BPackageManager
-keep class com.tm.box.fake.frameworks.BActivityManager
-keep class com.tm.box.fake.frameworks.BJobManager
-keep class com.tm.box.fake.frameworks.BStorageManager
-keep class com.tm.box.fake.frameworks.BUserManager
-keep class com.tm.box.fake.frameworks.BLocationManager
-keep class com.tm.box.fake.frameworks.BNotificationManager
-keep class com.tm.box.fake.frameworks.BAccountManager
-keep interface com.tm.box.core.system.** { *; }
-keep class com.tm.box.core.system.**$Stub { *; }
-keep class com.tm.box.core.system.**$Stub$Proxy { *; }

-keep class com.tm.box.fake.** { *; }
-keep class com.tm.box.core.system.user.BUserInfo { *; }
-keep class android.** { *; }
-keep class com.android.internal.** { *; }

-keep class com.tm.reflect.** { *; }
-keep @com.tm.reflect.annotation.BClass class * { *; }
-keep @com.tm.reflect.annotation.BClassName class * { *; }
-keep @com.tm.reflect.annotation.BClassNameNotProcess class * { *; }
-keepclasseswithmembernames class * {
    @com.tm.reflect.annotation.BField.* <methods>;
    @com.tm.reflect.annotation.BFieldNotProcess.* <methods>;
    @com.tm.reflect.annotation.BFieldSetNotProcess.* <methods>;
    @com.tm.reflect.annotation.BFieldCheckNotProcess.* <methods>;
    @com.tm.reflect.annotation.BMethod.* <methods>;
    @com.tm.reflect.annotation.BStaticField.* <methods>;
    @com.tm.reflect.annotation.BStaticMethod.* <methods>;
    @com.tm.reflect.annotation.BMethodCheckNotProcess.* <methods>;
    @com.tm.reflect.annotation.BConstructor.* <methods>;
    @com.tm.reflect.annotation.BConstructorNotProcess.* <methods>;
}