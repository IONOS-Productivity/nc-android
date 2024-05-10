package com.strato.hidrive.scanbot.screens.camera.use_case

import android.content.Context
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.strato.hidrive.common_ui.utils.getMatrixAccordingToExifOrientation
import javax.inject.Inject

internal class ExtractPictureTransformation @Inject constructor(
	private val context: Context,
) {

	operator fun invoke(data: Uri): Matrix? = context
		.contentResolver
		.openInputStream(data)
		?.let { ExifInterface(it) }
		?.let { getMatrixAccordingToExifOrientation(it) }
}