package com.ionos.player.cache

interface PlayerPathProvider {

	fun getCacheFolderPath(): String

	fun getPlayerCacheFolderName(): String

}