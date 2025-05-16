package com.ionos.player.media3.resumption

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import javax.inject.Inject

class PlaybackResumptionPlayerListener @Inject constructor(
	private val playbackResumptionRepository: PlaybackResumptionRepository,
) : Player.Listener {

	override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
		mediaItem?.let { playbackResumptionRepository.updateCurrentFileId(it.mediaId) }
	}
}
