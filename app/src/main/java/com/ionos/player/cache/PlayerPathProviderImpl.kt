package com.ionos.player.cache

import android.content.Context
import com.strato.hidrive.player.cache.PlayerPathProvider
import javax.inject.Inject

class PlayerPathProviderImpl @Inject constructor(
    private val context: Context,
): PlayerPathProvider {

    override fun getCacheFolderPath(): String {
        return this.context.cacheDir.toString()
    }

    override fun getPlayerCacheFolderName(): String {
        return "player"
    }

}