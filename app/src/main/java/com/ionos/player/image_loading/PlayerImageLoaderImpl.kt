package com.ionos.player.image_loading

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.image_loading.PlayerImageLoader
import com.strato.hidrive.player.image_loading.PlayerImageRequestBuilder
import javax.inject.Inject

class PlayerImageLoaderImpl @Inject constructor(): PlayerImageLoader {
    override fun load(model: PlayerFileInfo): PlayerImageRequestBuilder {
        TODO("Not yet implemented")
    }
}