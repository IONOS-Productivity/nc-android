package com.ionos.player.model.strategy.release

import com.ionos.player.model.PlaybackFile
import javax.inject.Inject

class DefaultPlaybackReleaseStrategy @Inject constructor() : PlaybackReleaseStrategy {

	override fun releaseCurrentPlayback(files: List<PlaybackFile>, currentFile: PlaybackFile): Boolean {
		return files.none { it.id == currentFile.id }
	}
}
