package com.ionos.player.cache

import androidx.annotation.WorkerThread
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.Cache
import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.transformation.FileInfoToCacheKeyTransformation
import javax.inject.Inject

@UnstableApi
class PlayerCacheCleaner @Inject constructor(
	private val cache: Cache,
	private val transformation: FileInfoToCacheKeyTransformation,
) {

	@WorkerThread
	fun clean(entity: PlayerFileInfo) {
			cache.removeResource(
				transformation.transform(entity)
			)
	}

}