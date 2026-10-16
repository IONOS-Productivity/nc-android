/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.controller

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.ionos.player.media3.PlaybackService
import kotlinx.coroutines.guava.await

class MediaControllerFactory(
    private val controllerListener: MediaController.Listener,
) {

    suspend fun create(context: Context): MediaController {
        val token = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        return MediaController
            .Builder(context, token)
            .setListener(controllerListener)
            .buildAsync()
            .await()
    }
}
