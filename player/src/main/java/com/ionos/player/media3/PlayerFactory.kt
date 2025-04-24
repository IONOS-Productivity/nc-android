/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.ionos.player.media3.source.HiDriveMediaSourceFactory
import com.ionos.player.transformation.MediaItemToDataSourceFactoryTransformation
import javax.inject.Inject

@UnstableApi
class PlayerFactory @Inject constructor(
	private val context: Context,
	private val mediaItemToDataSourceFactoryTransformation: MediaItemToDataSourceFactoryTransformation,
) {

	fun create(): Player {
		val renderersFactory = DefaultRenderersFactory(context)
			.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
		return ExoPlayer
			.Builder(context, renderersFactory)
			.setAudioAttributes(AudioAttributes.DEFAULT, true)
			.setTrackSelector(DefaultTrackSelector(context))
			.setMediaSourceFactory(
                HiDriveMediaSourceFactory(
                    mediaItemToDataSourceFactoryTransformation
                )
			)
			.build()
	}
}