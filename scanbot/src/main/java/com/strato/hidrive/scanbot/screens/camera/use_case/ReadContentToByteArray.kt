package com.strato.hidrive.scanbot.screens.camera.use_case

import android.content.Context
import android.net.Uri
import com.strato.hidrive.scanbot.exception.OpenFileException
import javax.inject.Inject

internal class ReadContentToByteArray @Inject constructor(
	private val context: Context,
) {

	operator fun invoke(uri: Uri): ByteArray = context
		.contentResolver
		.openInputStream(uri)
		.let { it ?: throw OpenFileException(uri.path) }
		.buffered()
		.readBytes()
}