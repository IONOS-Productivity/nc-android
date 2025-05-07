package com.ionos.player.media3.datasource

import android.content.Context
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