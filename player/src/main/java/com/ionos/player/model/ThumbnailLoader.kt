package com.ionos.player.model

import android.content.Context
import android.graphics.Bitmap
import java.util.concurrent.Future

interface ThumbnailLoader {

    fun load(
        context: Context,
        file: PlaybackFile,
        width: Int,
        height: Int,
    ): Future<Bitmap>
}
