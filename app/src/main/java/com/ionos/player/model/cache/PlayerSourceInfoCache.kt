package com.ionos.player.model.cache

import com.ionos.player.model.PlayerFileInfo
import com.owncloud.android.datamodel.OCFile

interface PlayerSourceInfoCache {

	operator fun set(key: PlayerFileInfo, value: OCFile): OCFile

	operator fun get(key: PlayerFileInfo): OCFile?

	fun clear()

}