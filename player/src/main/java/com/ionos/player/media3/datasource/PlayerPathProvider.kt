package com.ionos.player.media3.datasource

interface PlayerPathProvider {

	fun getCacheFolderPath(): String

	fun getPlayerCacheFolderName(): String

}