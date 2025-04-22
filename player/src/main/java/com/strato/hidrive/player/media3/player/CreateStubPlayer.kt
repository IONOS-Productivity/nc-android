package com.strato.hidrive.player.media3.player

import androidx.media3.common.Player
import javax.inject.Inject

class CreateStubPlayer @Inject constructor(
): CreatePlayer {

	override operator fun invoke(): Player {
		throw IllegalStateException("Attempting to create player via stub")
	}

}