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

-keep class com.tm.box.** {*; }
-keep class com.tm.jni.** {*; }
-keep class mirror.** {*; }
-keep class android.** {*; }
-keep class com.android.** {*; }

-keep class com.tm.reflect.** {*; }
-keep @com.tm.reflect.annotation.BClass class * {*;}
-keep @com.tm.reflect.annotation.BClassName class * {*;}
-keep @com.tm.reflect.annotation.BClassNameNotProcess class * {*;}
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