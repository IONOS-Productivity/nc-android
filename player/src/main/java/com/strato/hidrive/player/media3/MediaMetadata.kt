package com.strato.hidrive.player.media3

import android.os.Bundle
import androidx.media3.common.MediaMetadata

private const val MEDIA_ID_KEY = "mediaId"

fun MediaMetadata.Builder.setMediaId(mediaId: String): MediaMetadata.Builder {
	return setExtras(Bundle().apply { putString(MEDIA_ID_KEY, mediaId) })
}

val MediaMetadata.mediaId: String?
	get() = extras?.getString(MEDIA_ID_KEY)
