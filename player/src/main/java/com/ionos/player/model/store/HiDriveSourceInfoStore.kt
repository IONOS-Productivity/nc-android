package com.ionos.player.model.store

import com.ionos.player.model.PlayerFileInfo
import javax.inject.Inject

class HiDriveSourceInfoStore @Inject constructor() : SourceInfoStore {
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
			sourceInfos.firstOrNull { mediaId == it.id }
		}
	}

	override fun clear() {
		synchronized(this) {
			sourceInfos.clear()
		}
	}
}
