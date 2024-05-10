package com.strato.hidrive.common_ui.utils

import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface

fun getMatrixAccordingToExifOrientation(
	exifInterface: ExifInterface
): Matrix? {
	val matrix = Matrix()
	val orientation =
		exifInterface.getAttributeInt(
			ExifInterface.TAG_ORIENTATION,
			ExifInterface.ORIENTATION_UNDEFINED
		)

	return with(matrix) {
		when (orientation) {
			ExifInterface.ORIENTATION_ROTATE_90 -> rotate(90f)
			ExifInterface.ORIENTATION_ROTATE_180 -> rotate(180f)
			ExifInterface.ORIENTATION_ROTATE_270 -> rotate(270f)
			ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipHorizontal()
			ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipVertical()
			ExifInterface.ORIENTATION_TRANSPOSE -> flipHorizontal().rotate(270f)
			ExifInterface.ORIENTATION_TRANSVERSE -> flipHorizontal().rotate(90f)
			else -> null
		}
	}
}

private fun Matrix.rotate(angle: Float) =
	apply { setRotate(angle) }

private fun Matrix.flipHorizontal() =
	apply { postScale(-1f, 1f) }

private fun Matrix.flipVertical() =
	apply { postScale(1f, -1f) }