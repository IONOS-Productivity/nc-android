/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.media3.datasource

import androidx.annotation.WorkerThread
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheKeyFactory
import com.ionos.player.model.PlaybackFile
import javax.inject.Inject

class CacheCleaner @Inject constructor(
    @UnstableApi private val cache: Cache,
) {

	@WorkerThread
    @UnstableApi
	fun clean(file: PlaybackFile) {
		val dataSpec = DataSpec.Builder()
			.setUri(file.uri)
			.build()
		val cacheKey = CacheKeyFactory.DEFAULT.buildCacheKey(dataSpec)
		cache.removeResource(cacheKey)
	}

}