package com.strato.hidrive.views.exif_info

import android.content.Context

interface ExifInfoShowError {
	fun invoke(context: Context, text: String?)
}