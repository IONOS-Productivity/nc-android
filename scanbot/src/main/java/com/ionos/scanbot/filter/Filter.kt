package com.ionos.scanbot.filter

import android.graphics.Bitmap
import io.scanbot.sdk.process.ImageProcessor


internal interface Filter {
	fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap?
}