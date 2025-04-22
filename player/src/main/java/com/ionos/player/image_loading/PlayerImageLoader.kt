package com.ionos.player.image_loading

import com.ionos.player.domain.PlayerFileInfo

interface PlayerImageLoader {

	fun load(model: PlayerFileInfo): PlayerImageRequestBuilder?

}