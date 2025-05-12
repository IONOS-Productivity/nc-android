package com.ionos.player.model

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.owncloud.android.utils.glide.CustomGlideStreamLoader
import java.util.concurrent.Future
import javax.inject.Inject

class ThumbnailLoaderImpl @Inject constructor(
    userAccountManager: UserAccountManager,
    clientFactory: ClientFactory,
) : ThumbnailLoader {
    private val user = userAccountManager.user
    private val modelLoader = CustomGlideStreamLoader(user, clientFactory)
    private val getThumbnailUrl = clientFactory.create(user).baseUri.toString() + "/index.php/core/preview"

    override fun load(
        context: Context,
        file: PlaybackFile,
        width: Int,
        height: Int,
    ): Future<Bitmap> {
        return Glide
            .with(context)
            .using(modelLoader)
            .load("$getThumbnailUrl?fileId=${file.id}&x=$width&y=$height&a=1&mode=cover&forceIcon=0")
            .asBitmap()
            .into(width, height)
    }
}
