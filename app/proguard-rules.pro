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

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.merseyside.partyapp.**$$serializer { *; }
-keepclassmembers class com.merseyside.partyapp.** {
    *** Companion;
}
-keepclasseswithmembers class com.merseyside.partyapp.** {
    kotlinx.serialization.KSerializer serializer(...);
}

#-keep,includedescriptorclasses class com.merseyside.archy.**$$serializer { *; }
#-keepclassmembers class com.merseyside.archy.** {
#    *** Companion;
#}
#-keepclasseswithmembers class com.merseyside.archy.** {
#    kotlinx.serialization.KSerializer serializer(...);
#}

-keep class org.apache.http.** {
    *;
}
-keep class !com.merseyside.**

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field
-ignorewarnings