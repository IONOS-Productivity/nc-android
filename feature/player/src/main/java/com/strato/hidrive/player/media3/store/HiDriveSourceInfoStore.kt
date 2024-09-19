package com.strato.hidrive.player.media3.store

import com.strato.hidrive.player.domain.PlayerFileInfo
import com.viseven.develop.media3.item.MediaIdFactory
import com.viseven.develop.media3.store.SourceInfoStore
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
