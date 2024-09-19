package com.strato.hidrive.views.exif_info.transformation

fun interface ExifInfoTransformation<In, Out> {
	fun transform(value: In): Out
}