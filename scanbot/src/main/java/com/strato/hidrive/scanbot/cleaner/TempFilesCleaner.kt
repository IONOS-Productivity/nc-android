package com.strato.hidrive.scanbot.cleaner

import com.strato.hidrive.scanbot.provider.FileProvider
import com.strato.hidrive.scanbot.util.io.removeRecursive
import javax.inject.Inject

internal class TempFilesCleaner @Inject constructor(
	private val fileProvider: FileProvider,
) {

	fun clean(): Unit = with(fileProvider) {
		sdkTempFilesDirectory.removeRecursive()
		cacheDirectory.removeRecursive()
		imageResultsDirectory.removeRecursive()
		tempOperationsDirectory.removeRecursive()
	}
}
