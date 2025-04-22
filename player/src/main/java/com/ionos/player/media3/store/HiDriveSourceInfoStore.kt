package com.ionos.player.media3.store

import com.ionos.player.domain.PlayerFileInfo
import com.ionos.player.media3.item.MediaIdFactory
import javax.inject.Inject

class HiDriveSourceInfoStore @Inject constructor(
	private val mediaIdFactory: MediaIdFactory<PlayerFileInfo>,
) : SourceInfoStore<PlayerFileInfo> {
	private val sourceInfos = mutableListOf<PlayerFileInfo>()

	override fun getSourceInfos(): List<PlayerFileInfo> {
		return synchronized(this) {
			sourceInfos.toList()
		}
	}

	override fun setSourceInfos(sourceInfos: List<PlayerFileInfo>) {
		synchronized(this) {
			this.sourceInfos.clear()
			this.sourceInfos.addAll(sourceInfos)
		}
	}

	override fun getSourceInfo(mediaId: String): PlayerFileInfo? {
		return synchronized(this) {
			sourceInfos.firstOrNull { mediaId == mediaIdFactory.create(it) }
		}
	}

	override fun clear() {
		synchronized(this) {
			sourceInfos.clear()
		}
	}
}
