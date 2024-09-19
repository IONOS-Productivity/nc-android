package com.strato.hidrive.player.cache

interface PlayerPathProvider {

	fun getCacheFolderPath(): String

	fun getPlayerCacheFolderName(): String

}