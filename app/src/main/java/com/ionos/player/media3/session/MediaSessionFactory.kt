/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.session

import android.content.Context
import android.os.Bundle
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.ionos.player.media3.common.PlayerFactory
import com.ionos.player.media3.resumption.PlaybackResumptionPlayerListener
import com.ionos.player.model.ThumbnailLoader
import com.owncloud.android.R
import javax.inject.Inject

class MediaSessionFactory @Inject constructor(
    private val context: Context,
    private val playerFactory: PlayerFactory,
    private val sessionCallback: MediaSessionCallback,
    private val resumptionPlayerListener: PlaybackResumptionPlayerListener,
    private val thumbnailLoader: ThumbnailLoader,
) {

    @UnstableApi
    fun create(): MediaSession {
        val player = playerFactory.create()
        player.addListener(resumptionPlayerListener)
        return MediaSession
            .Builder(context, player)
            .setBitmapLoader(provideBitmapLoader())
            .setCallback(sessionCallback)
            .setCustomLayout(provideCustomLayout())
            .build()
    }

    private fun provideCustomLayout(): List<CommandButton> {
        return listOf(
            CommandButton
                .Builder()
                .setDisplayName(context.getString(R.string.player_media_controls_close_action_title))
                .setIconResId(R.drawable.player_ic_close)
                .setSessionCommand(SessionCommand(MediaSessionCallback.CLOSE_ACTION, Bundle.EMPTY))
                .build(),
        )
    }

    @UnstableApi
    private fun provideBitmapLoader(): BitmapLoader {
        return MediaSessionBitmapLoader(context, thumbnailLoader)
    }
}
