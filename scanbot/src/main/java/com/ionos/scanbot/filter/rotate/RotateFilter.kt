package com.ionos.scanbot.filter.rotate

import android.graphics.Bitmap
import com.ionos.scanbot.filter.Filter
import com.ionos.scanbot.util.graphics.rotate
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 04.12.2017.
 */
 data class RotateFilter(val degrees: Float) : Filter {
	override fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap) = bitmap.rotate(degrees)
}