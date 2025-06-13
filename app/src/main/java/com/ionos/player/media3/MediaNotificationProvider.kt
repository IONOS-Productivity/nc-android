package com.ionos.player.media3

import android.content.Context
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.DefaultMediaNotificationProvider
import com.ionos.player.media3.common.playbackFile
import javax.inject.Inject

@UnstableApi
class MediaNotificationProvider @Inject constructor(
    context: Context,
) : DefaultMediaNotificationProvider(context) {

    override fun getNotificationContentTitle(metadata: MediaMetadata): CharSequence? {
        return if (metadata.title.isNullOrEmpty()) {
            metadata.playbackFile?.getNameWithoutExtension()
        } else {
            metadata.title
        }
    }
}
