package com.strato.hidrive.views.exif_info

import android.widget.ImageView
import io.reactivex.Completable

fun interface ExifInfoLoadHeader {
	fun invoke(
		to: ImageView,
		width: Int,
		height: Int,
	): Completable
}