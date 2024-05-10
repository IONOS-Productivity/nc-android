package com.strato.hidrive.scanbot.entity

import android.graphics.Bitmap.CompressFormat

 enum class ImageFormat(
	val compressFormat: CompressFormat,
	val extension: String,
) {
	JPEG(CompressFormat.JPEG, "jpg"),
	PNG(CompressFormat.PNG, "png"),
}
