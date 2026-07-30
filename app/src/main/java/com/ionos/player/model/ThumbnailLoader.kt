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