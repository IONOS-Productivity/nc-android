package com.strato.hidrive.views.exif_info

import io.reactivex.Single

fun interface ExifInfoLoadMetaData {
	fun invoke(): Single<ExifMetaData>
}