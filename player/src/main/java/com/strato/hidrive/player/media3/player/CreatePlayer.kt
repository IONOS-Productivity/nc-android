package com.strato.hidrive.player.media3.player

import androidx.media3.common.Player

interface CreatePlayer {

	operator fun invoke(): Player

}