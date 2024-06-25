package com.ionos.scanbot.filter.crop

import android.graphics.Bitmap
import com.ionos.scanbot.entity.SelectedContour
import com.ionos.scanbot.filter.Filter
import io.scanbot.sdk.process.CropOperation
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */
internal data class CropFilter(val contour: SelectedContour) : Filter {

	override fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap? {
		return imageProcessor.processBitmap(bitmap, listOf(CropOperation(contour.normalizedPolygon)))
	}
}