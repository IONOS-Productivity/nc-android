package com.ionos.player.model

import android.content.Context
import android.graphics.Bitmap
import java.util.concurrent.Future

interface PlayerImageLoader {

    fun load(
        context: Context,
        file: PlayerFileInfo,
        width: Int,
        height: Int,
    ): Future<Bitmap>
}
