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
-keepclassmembers class com.pascoapp.wba02_android.views.takeTest.questionTypes.DiscussionInterfaceToWebview {
   public *;
}
-keepclassmembers class com.pascoapp.wba02_android.views.takeTest.questionTypes.McqFragment.McqWebAppInterface {
   public *;
}
-keepclassmembers class com.pascoapp.wba02_android.views.takeTest.questionTypes.FillInFragment.FillInWebAppInterface {
   public *;
}



# Retrolambda
-dontwarn java.lang.invoke.*

# Chunk templating engine
-dontwarn com.x5.**
-keep class com.x5.template.** { *; }
-keep class com.x5.util.* { *; }