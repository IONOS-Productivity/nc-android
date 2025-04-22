package com.ionos.player.image_loading

import com.ionos.player.domain.PlayerFileInfo
import javax.inject.Inject

class PlayerImageLoaderImpl @Inject constructor(): PlayerImageLoader {
    override fun load(model: PlayerFileInfo): PlayerImageRequestBuilder {
        TODO("Not yet implemented")
    }
}