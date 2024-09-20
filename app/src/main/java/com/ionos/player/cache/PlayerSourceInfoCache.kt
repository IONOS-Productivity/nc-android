package com.ionos.player.cache

import com.owncloud.android.datamodel.OCFile
import com.strato.hidrive.player.domain.PlayerFileInfo

interface PlayerSourceInfoCache {

	operator fun set(key: PlayerFileInfo, value: OCFile): OCFile

	operator fun get(key: PlayerFileInfo): OCFile?

	fun clear()

}