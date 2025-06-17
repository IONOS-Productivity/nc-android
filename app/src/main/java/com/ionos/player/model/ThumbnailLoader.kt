package com.ionos.player.model

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.owncloud.android.utils.glide.CustomGlideStreamLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Future
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ThumbnailLoader @Inject constructor(
    userAccountManager: UserAccountManager,
    clientFactory: ClientFactory,
) {
    private val user by lazy { userAccountManager.user }
    private val modelLoader by lazy { CustomGlideStreamLoader(user, clientFactory) }
    private val getThumbnailUrl by lazy { clientFactory.create(user).baseUri.toString() + "/index.php/core/preview" }

    suspend fun await(context: Context, file: PlaybackFile, width: Int, height: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                try {
                    val thumbnail = load(context, file, width, height).get()
                    continuation.resume(thumbnail)
                } catch (e: Exception) {
                    continuation.resume(null)
                }
            }
        }
    }

    fun load(context: Context, file: PlaybackFile, width: Int, height: Int): Future<Bitmap> {
        return Glide
            .with(context)
            .using(modelLoader)
            .load("$getThumbnailUrl?fileId=${file.id}&x=$width&y=$height&a=1&mode=cover&forceIcon=0")
            .asBitmap()
            .into(width, height)
    }
}
