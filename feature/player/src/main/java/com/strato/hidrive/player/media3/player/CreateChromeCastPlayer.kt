package com.strato.hidrive.player.media3.player

import androidx.media3.cast.MediaItemConverter
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.strato.hidrive.player.chromecast.PlayerCastContextProvider
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.media3.cast.HiDriveCastPlayer
import com.strato.hidrive.player.predicate.AllowedChromecastMediaFormatPredicate
import com.viseven.develop.media3.store.SourceInfoStore
import javax.inject.Inject

@UnstableApi
class CreateChromeCastPlayer @Inject constructor(
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val allowedChromecastMediaFormatPredicate: AllowedChromecastMediaFormatPredicate,
	private val castContextProvider: PlayerCastContextProvider,
	private val mediaItemConverter: MediaItemConverter,
): CreatePlayer {

	override operator fun invoke(): Player {
		val castContext = castContextProvider.requireContext()
		return HiDriveCastPlayer(
			castContext,
			mediaItemConverter,
			sourceInfoStore,
			allowedChromecastMediaFormatPredicate,
		)
	}

}