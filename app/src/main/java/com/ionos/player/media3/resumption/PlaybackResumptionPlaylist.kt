package com.ionos.player.media3.resumption

import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import com.ionos.player.model.PlaybackFile

data class PlaybackResumptionPlaylist(
	@UnstableApi val mediaItemsWithStartPosition: MediaItemsWithStartPosition,
	val playbackFiles: List<PlaybackFile>,
)
