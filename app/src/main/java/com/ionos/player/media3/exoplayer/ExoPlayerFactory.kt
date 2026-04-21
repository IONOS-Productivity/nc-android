/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.exoplayer

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.ionos.player.media3.common.PlayerFactory
import javax.inject.Inject

class ExoPlayerFactory @Inject constructor(
    private val context: Context,
    private val mediaSourceFactory: MediaSourceFactory,
) : PlayerFactory {

    @UnstableApi
    override fun create(): Player {
        val renderersFactory = DefaultRenderersFactory(context)
            .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        return ExoPlayer
            .Builder(context, renderersFactory)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setTrackSelector(DefaultTrackSelector(context))
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
    }
}