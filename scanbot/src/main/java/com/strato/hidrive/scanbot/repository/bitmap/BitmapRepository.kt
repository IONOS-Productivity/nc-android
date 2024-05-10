package com.strato.hidrive.scanbot.repository.bitmap

import android.graphics.Bitmap
import com.strato.hidrive.scanbot.provider.FileProvider
import com.strato.hidrive.scanbot.util.io.removeRecursive
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */

//TODO alk - make the class internal
 class BitmapRepository @Inject constructor(
	private val bitmapSaver: BitmapSaver,
	private val fileProvider: FileProvider,
) {

	fun create(bitmap: Bitmap): String {
		val id = UUID.randomUUID().toString()
		saveBitmapInFileSystem(id, bitmap)
		return id
	}

	fun read(id: String): Bitmap? {
		return getBitmapSaver(id).load()
	}

	fun readFile(id: String): File {
		return getBitmapSaver(id).loadFile()
	}

	fun delete(id: String) {
		val cacheDirectoryPath = fileProvider.cacheDirectory.path
		File(cacheDirectoryPath, getFileName(id)).removeRecursive()
	}

	fun deleteAll() {
		fileProvider.cacheDirectory.removeRecursive()
	}

	fun release() {
		deleteAll()
	}

	private fun saveBitmapInFileSystem(id: String, bitmap: Bitmap) {
		getBitmapSaver(id).save(bitmap)
	}

	private fun getFileName(id: String) = "${id}_tempFile.jpg"

	private fun getBitmapSaver(id: String): BitmapSaver = bitmapSaver
		.setFileName(getFileName(id))
		.setDirectoryName(FileProvider.CACHE_DIRECTORY_NAME)
}