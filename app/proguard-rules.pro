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

#-keep enum com.judahben149.spendr.* { *; }
#-keep enum com.airbnb.* { *; }
#-keep enum com.tbuonomo.* { *; }
#-keepnames class com.judahben149.spendr.data.local.entity.*
#-keepnames class com.judahben149.spendr.domain.model.*


-keepclassmembers class * extends androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
}

#-keep class com.itextpdf.** { *; }
#-dontwarn com.itextpdf.*
#-keep class javax.xml.crypto.dsig.** { *; }
#-dontwarn javax.xml.crypto.dsig.**
#-keep class javax.xml.crypto.** { *; }
#-dontwarn javax.xml.crypto.**
#
#-keep class org.spongycastle.** { *; }
#-dontwarn org.spongycastle.**

-dontwarn java.awt.Canvas
-dontwarn java.awt.Color
-dontwarn java.awt.Image
-dontwarn java.awt.image.BufferedImage
-dontwarn java.awt.image.ColorModel
-dontwarn java.awt.image.ImageProducer
-dontwarn java.awt.image.MemoryImageSource
-dontwarn java.awt.image.PixelGrabber
-dontwarn javax.imageio.ImageIO
-dontwarn org.slf4j.impl.StaticLoggerBinder

#
#-keepclassmembers class com.judahben149.spendr.databinding.**  {
#    public <methods>;
#}

#-keep class * implements androidx.viewbinding.ViewBinding {
#    public static *** bind(android.view.View);
#    public static *** inflate(android.view.LayoutInflater);
#   }
#
# -keepclassmembers class com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel { *; }
#
# -keep class java.lang.Class { *; }

# -keepattributes Signature

# -keep class com.airbnb.epoxy.** { *; }
#
#-keep class * extends com.airbnb.epoxy.EpoxyController { *; }
#-keep class * extends com.airbnb.epoxy.ControllerHelper { *; }
