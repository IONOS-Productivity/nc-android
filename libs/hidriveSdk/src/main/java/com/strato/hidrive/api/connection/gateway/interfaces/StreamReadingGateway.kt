package com.strato.hidrive.api.connection.gateway.interfaces

import java.io.IOException
import java.io.OutputStream

/**
 * User: Dima Muravyov
 * Date: 12.03.2018
 */
interface StreamReadingGateway<Entity> : Gateway<Entity> {

	interface Listener {

		@Throws(IOException::class)
		fun onPrepareOutputStream(): OutputStream?

		fun onDownloadProgress(downloaded: Long, totalSize: Long)
	}

	fun isDownloading(): Boolean

	fun stop()
}