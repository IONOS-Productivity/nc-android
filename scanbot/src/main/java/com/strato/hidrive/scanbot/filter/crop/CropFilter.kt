package com.strato.hidrive.scanbot.filter.crop

import android.graphics.Bitmap
import com.strato.hidrive.scanbot.entity.SelectedContour
import com.strato.hidrive.scanbot.filter.Filter
import io.scanbot.sdk.process.CropOperation
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */
//TODO alk - add internal
 data class CropFilter(val contour: SelectedContour) : Filter {

	override fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap? {
		return imageProcessor.processBitmap(bitmap, listOf(CropOperation(contour.normalizedPolygon)))
	}
}