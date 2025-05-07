package com.ionos.player.model.image_loader

import com.ionos.player.model.PlayerFileInfo

interface PlayerImageLoader {

	fun load(model: PlayerFileInfo): PlayerImageRequestBuilder?

}