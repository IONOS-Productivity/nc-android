package com.ionos.scanbot.filter

import android.graphics.Bitmap
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */
internal interface Filter {
	fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap?
}