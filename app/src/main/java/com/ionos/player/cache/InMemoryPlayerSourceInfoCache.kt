package com.ionos.player.cache

import com.owncloud.android.datamodel.OCFile
import com.ionos.player.domain.PlayerFileInfo
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class InMemoryPlayerSourceInfoCache @Inject constructor(
): PlayerSourceInfoCache {

	private val map = ConcurrentHashMap<PlayerFileInfo, OCFile>()

	override fun set(key: PlayerFileInfo, value: OCFile): OCFile {
		return map.put(key, value) ?: value
	}

	override fun get(key: PlayerFileInfo): OCFile? {
		return map[key]
	}

	override fun clear() {
		map.clear()
	}

}