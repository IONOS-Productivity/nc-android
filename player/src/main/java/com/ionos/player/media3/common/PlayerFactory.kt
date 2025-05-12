package com.ionos.player.media3.common

import androidx.media3.common.Player

interface PlayerFactory {
	fun create(): Player
}