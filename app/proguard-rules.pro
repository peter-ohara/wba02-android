# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/peter/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class com.pascoapp.wba02_android.takeTest.questionTypes.DiscussionInterfaceToWebview {
   public *;
}
-keepclassmembers class com.pascoapp.wba02_android.takeTest.questionTypes.McqWebAppInterface {
   public *;
}
-keepclassmembers class com.pascoapp.wba02_android.takeTest.questionTypes.FillInWebAppInterface {
   public *;
}


# Firebase Database and Firebase Authentication
-keepattributes Signature
-keepattributes *Annotation*

# This rule will properly ProGuard all the model classes in
# the package com.pascoapp.wba02_android.services.
-keepclassmembers class com.pascoapp.wba02_android.services.** {
  *;
}


# Retrolambda
-dontwarn java.lang.invoke.*

# Chunk templating engine
-dontwarn com.x5.**
-keep class com.x5.template.** { *; }
-keep class com.x5.util.* { *; }

##-------------Begin Settings For Guava ------------------------------------------------------------
#https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-guava.pro
# Configuration for Guava
#
# disagrees with instructions provided by Guava project: https://code.google.com/p/guava-libraries/wiki/UsingProGuardWithGuava
#
# works if you add the following line to the Gradle dependencies
#
# provided 'javax.annotation:jsr250-api:1.0'
-keep class com.google.common.io.Resources {
    public static <methods>;
}
-keep class com.google.common.collect.Lists {
    public static ** reverse(**);
}
-keep class com.google.common.base.Charsets {
    public static <fields>;
}

-keep class com.google.common.base.Joiner {
    public static Joiner on(String);
    public ** join(...);
}

-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**

##-------------End Settings For Guava --------------------------------------------------------------