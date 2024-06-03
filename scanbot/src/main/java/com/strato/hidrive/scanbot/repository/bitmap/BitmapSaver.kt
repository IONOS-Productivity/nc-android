package com.strato.hidrive.scanbot.repository.bitmap

import android.content.Context
import android.graphics.Bitmap
import android.system.ErrnoException
import android.system.OsConstants
import android.util.Log
import com.ionos.domain.exception.NoFreeLocalSpaceException
import com.strato.hidrive.scanbot.entity.ImageFormat
import com.strato.hidrive.scanbot.util.io.SaveErrorOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

/**
 * Created by: Alex Kucherenko
 * Date: 28.11.2017.
 */
//TODO alk - add internal
 class BitmapSaver @Inject constructor(
	private val context: Context,
	private val bitmapDecoder: BitmapDecoder,
) {
	companion object {
		private const val DEFAULT_BITMAP_QUALITY = 100
	}

	private var bitmapQuality = DEFAULT_BITMAP_QUALITY
	private var imageFormat = ImageFormat.JPEG
	private var directoryName = "images"
	private var fileName = "image"

	fun setFileName(fileName: String): BitmapSaver = apply {
		this.fileName = fileName
	}

	fun setDirectoryName(directoryName: String): BitmapSaver = apply {
		this.directoryName = directoryName
	}

	fun setImageFormat(imageFormat: ImageFormat): BitmapSaver = apply {
		this.imageFormat = imageFormat
	}

	fun setBitmapQuality(bitmapQuality: Int): BitmapSaver = apply {
		this.bitmapQuality = bitmapQuality
	}

	fun save(bitmap: Bitmap): Unit = try {
		val fileOutputStream = FileOutputStream(createFile())
		SaveErrorOutputStream(fileOutputStream).write(bitmap)
	} catch (e: IOException) {
		onSaveError(e)
	}

	fun load(): Bitmap? = try {
		bitmapDecoder.decodeSampledBitmap(createFile())
	} catch (e: Exception) {
		e.printStackTrace()
		null
	}

	fun loadFile(): File {
		return createFile()
	}

	private fun createFile(): File {
		val directory = context.getDir(directoryName, Context.MODE_PRIVATE)
		if (!directory.exists() && !directory.mkdirs()) {
			Log.e("BitmapSaver", "Error creating directory $directory")
		}
		return File(directory, fileName)
	}

	private fun SaveErrorOutputStream.write(bitmap: Bitmap): Unit = use {
		val compressFormat = imageFormat.compressFormat
		bitmap.compress(compressFormat, bitmapQuality, this)
		error?.let { throw it }
	}

	private fun onSaveError(error: IOException) {
		val cause = error.cause
		if (cause is ErrnoException && cause.errno == OsConstants.ENOSPC) {
			throw NoFreeLocalSpaceException()
		} else {
			throw error
		}
	}
}
