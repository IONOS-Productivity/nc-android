package com.strato.hidrive.views.exif_info

import androidx.annotation.DrawableRes

data class ExifInfoFile(
	val fileName: String,
	val contentLength: Long,
	val lastModified: Long,
	@DrawableRes val iconResource: Int,
)
