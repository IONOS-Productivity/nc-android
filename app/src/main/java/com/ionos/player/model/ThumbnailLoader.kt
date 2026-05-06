/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.StringSignature
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.owncloud.android.utils.glide.CustomGlideStreamLoader
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.Future
import javax.inject.Inject
import kotlin.coroutines.resume

class ThumbnailLoader @Inject constructor(
    userAccountManager: UserAccountManager,
    clientFactory: ClientFactory,
) {
    private val user by lazy { userAccountManager.user }
    private val modelLoader by lazy { CustomGlideStreamLoader(user, clientFactory) }
    private val getThumbnailUrl by lazy { clientFactory.create(user).baseUri.toString() + "/index.php/core/preview" }

    suspend fun await(context: Context, file: PlaybackFile, width: Int, height: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { continuation ->
                try {
                    val future = load(context, file, width, height)
                    continuation.invokeOnCancellation { future.cancel(true) }
                    continuation.resume(future.get())
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    continuation.resume(null)
                }
import androidx.core.graphics.drawable.toBitmap
import com.nextcloud.client.account.UserAccountManager
import com.nextcloud.client.network.ClientFactory
import com.nextcloud.utils.GlideHelper
import com.owncloud.android.lib.common.OwnCloudClientManagerFactory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ThumbnailLoader @Inject constructor(
    private val userAccountManager: UserAccountManager,
    private val clientFactory: ClientFactory,
) {
    private val user by lazy { userAccountManager.user }
    private val getThumbnailUrl by lazy {
        clientFactory.create(user).baseUri.toString() + "/index.php/core/preview"
    }

    suspend fun await(context: Context, file: PlaybackFile, width: Int, height: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OwnCloudClientManagerFactory.getDefaultSingleton()
                    .getNextcloudClientFor(user.toOwnCloudAccount(), context)

                val url = "$getThumbnailUrl?fileId=${file.id}&x=$width&y=$height&a=1&mode=cover&forceIcon=0"

                val drawable = GlideHelper.getDrawable(context, client, url)
                drawable?.toBitmap(width, height)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                null
            }
        }
    }

    fun load(context: Context, file: PlaybackFile, width: Int, height: Int): Future<Bitmap> {
        return Glide
            .with(context)
            .using(modelLoader)
            .load("$getThumbnailUrl?fileId=${file.id}&x=$width&y=$height&a=1&mode=cover&forceIcon=0")
            .asBitmap()
            .signature(StringSignature(file.id))
            .into(width, height)
    }

    fun load(context: Context, model: Any, fileId: String?, width: Int, height: Int): Future<Bitmap> {
        return Glide
            .with(context)
            .load(model)
            .asBitmap()
            .signature(StringSignature(fileId ?: model.toString()))
            .into(width, height)
    }

    fun load(imageView: ImageView, model: Any, fileId: String) {
        Glide
            .with(imageView.context)
            .load(model)
            .signature(StringSignature(fileId))
            .into(imageView)
    }
}
    suspend fun load(context: Context, file: PlaybackFile, width: Int, height: Int): Bitmap? {
        return await(context, file, width, height)
    }

    suspend fun load(context: Context, model: Any, fileId: String?, width: Int, height: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OwnCloudClientManagerFactory.getDefaultSingleton()
                    .getNextcloudClientFor(user.toOwnCloudAccount(), context)

                val url = when (model) {
                    is String -> model
                    else -> model.toString()
                }

                val drawable = GlideHelper.getDrawable(context, client, url)
                drawable?.toBitmap(width, height)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun load(imageView: ImageView, model: Any, fileId: String) {
        withContext(Dispatchers.IO) {
            try {
                val client = OwnCloudClientManagerFactory.getDefaultSingleton()
                    .getNextcloudClientFor(user.toOwnCloudAccount(), imageView.context)

                val url = when (model) {
                    is String -> model
                    else -> model.toString()
                }

                val drawable = GlideHelper.getDrawable(imageView.context, client, url)

                withContext(Dispatchers.Main) {
                    drawable?.let {
                        imageView.setImageDrawable(it)
                    }
                }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}