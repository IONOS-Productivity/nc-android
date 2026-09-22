/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.exoplayer

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.extractor.DefaultExtractorsFactory
import com.ionos.player.media3.datasource.DataSourceFactory
import javax.inject.Inject

class MediaSourceFactory @Inject constructor(
    private val dataSourceFactory: DataSourceFactory,
) : MediaSource.Factory {

    @UnstableApi
    override fun setDrmSessionManagerProvider(drmSessionManagerProvider: DrmSessionManagerProvider): MediaSource.Factory {
        return this
    }

    @UnstableApi
    override fun setLoadErrorHandlingPolicy(loadErrorHandlingPolicy: LoadErrorHandlingPolicy): MediaSource.Factory {
        return this
    }

    @UnstableApi
    override fun getSupportedTypes(): IntArray {
        return intArrayOf(C.CONTENT_TYPE_OTHER)
    }

    @UnstableApi
    override fun createMediaSource(mediaItem: MediaItem): MediaSource {
        return ProgressiveMediaSource
            .Factory(dataSourceFactory, DefaultExtractorsFactory())
            .createMediaSource(mediaItem)
    }
}
