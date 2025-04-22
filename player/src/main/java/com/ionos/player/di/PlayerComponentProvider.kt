package com.ionos.player.di

import com.ionos.player.media3.PlaybackServiceComponentProvider

interface PlayerComponentProvider : PlaybackServiceComponentProvider {
	override fun getComponent(): PlayerComponent
}
