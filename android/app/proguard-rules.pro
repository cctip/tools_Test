# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/Cellar/android-sdk/24.3.3/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# 如果项目中使用到注解，需要保留注解属性
-keepattributes *Annotation*

# 屏蔽警告
-ignorewarnings

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#表示不混淆枚举中的values()和valueOf()方法
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
#Gson混淆配置
 #避免混淆泛型, 这在JSON实体映射时非常重要
 -dontwarn com.google.gson.**
 -keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *;}
# Application classes that will beserialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep interface com.google.gson.**{*;}

 #当前项目下的资源配置
 -keep class com.relicroverblack.relicrovergo.db.** {*;}
 -keep class com.relicroverblack.relicrovergo.rnmodule.ToolModule {
    public *;
 }
 -keep class com.relicroverblack.relicrovergo.databinding.** {*;}
 -keep class com.relicroverblack.relicrovergo.ui.** {*;}

-keep public class * extends android.app.Application

# appsflyer
-keep class com.appsflyer.** { *; }
# Google Play Install Referrer for appsflyer
-keep public class com.android.installreferrer.** { *; }