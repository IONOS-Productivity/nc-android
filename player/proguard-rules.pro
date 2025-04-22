# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yaz/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.viseven.develop.player.volume.Volume { *; }
-keep class com.viseven.develop.multipleplayer.multiple_playback_error_strategy.SwitchToNextSourceMultiplePlaybackErrorStrategy { *; }
-keep class com.viseven.develop.player.video_view_setter.MediaPlayerVideoViewSetter { *; }
-keep class com.viseven.develop.multipleplayermvp.settings.InMemoryMultiplePlaybackSettings { *; }
-keep class com.viseven.develop.multipleplayer.multiple_playback_error_strategy.DoNotSwitchToNextSourceMultiplePlaybackErrorStrategy { *; }
-keep class com.viseven.develop.multipleplayer.player_source_release_strategy.DoNotReleaseSourceInfoReleaseStrategy { *; }
-keep class com.viseven.develop.multipleplayermvp.presenter.CompositeMultiplePlayerBasePresenter { *; }

# This prevents the names of native methods from being obfuscated.
-keepclasseswithmembernames class * { native <methods>; }

# Some members of these classes are being accessed from native methods. Keep them unobfuscated.
-keep class androidx.media3.decoder.flac.FlacDecoderJni { *; }
-keep class androidx.media3.extractor.FlacStreamMetadata { *; }
-keep class androidx.media3.extractor.metadata.flac.PictureFrame { *; }
