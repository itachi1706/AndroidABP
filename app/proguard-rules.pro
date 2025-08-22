##---------------Begin: Attribouter-specific configuration --------

# For xml-specified drawable resources
-keep class com.itachi1706.abp.attribouter.R$*
-keepclassmembers class com.itachi1706.abp.attribouter.R$* {
    public static <fields>;
}

# For wedge construction (from xml parser)
-keep class * extends com.itachi1706.abp.attribouter.wedges.Wedge

##---------------End: Attribouter-specific configuration ----------

##---------------Begin: proguard configuration for Ktor -------

-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**

# Coroutines: https://github.com/ktorio/ktor/issues/1354
-keepclassmembers class kotlinx.** {
    volatile <fields>;
}
-keepclassmembers class io.ktor.** {
    volatile <fields>;
}

##---------------End: proguard configuration for Ktor ---------

##---------------Begin: proguard configuration for gitrest -------

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.itachi1706.abp.gitrest.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.itachi1706.abp.gitrest.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.itachi1706.abp.gitrest.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}

-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}
-keep class *$$serializer { *; }

##---------------End: proguard configuration for gitrest ---------
