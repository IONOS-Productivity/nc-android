package com.strato.hidrive.player.image_loading

import com.strato.hidrive.player.domain.PlayerFileInfo

interface PlayerImageLoader {

	fun load(model: PlayerFileInfo): PlayerImageRequestBuilder?

}