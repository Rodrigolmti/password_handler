-verbose
-forceprocessing
-optimizationpasses 5

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Kotlin
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }

#Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Fabric
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keepnames public class * extends android.support.v4.app.Fragment
