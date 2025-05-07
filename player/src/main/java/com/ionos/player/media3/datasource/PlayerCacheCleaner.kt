package com.ionos.player.media3.datasource

import androidx.annotation.WorkerThread
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheKeyFactory
import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

@UnstableApi
class PlayerCacheCleaner @Inject constructor(
	private val cache: Cache,
) {

	@WorkerThread
	fun clean(entity: PlayerFileInfo) {
		val dataSpec = DataSpec.Builder()
			.setUri(entity.uri)
			.build()
		val cacheKey = CacheKeyFactory.DEFAULT.buildCacheKey(dataSpec)
		cache.removeResource(cacheKey)
	}

}