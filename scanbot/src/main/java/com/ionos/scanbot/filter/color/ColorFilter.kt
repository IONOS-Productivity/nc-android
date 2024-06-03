package com.ionos.scanbot.filter.color

import android.graphics.Bitmap
import com.ionos.scanbot.filter.Filter
import io.scanbot.sdk.process.FilterOperation
import io.scanbot.sdk.process.ImageFilterTuneType
import io.scanbot.sdk.process.ImageProcessor
import io.scanbot.sdk.process.TuneOperation

/**
 * Created by: Alex Kucherenko
 * Date: 24.11.2017.
 */
//TODO alk - add internal
 data class ColorFilter(val colorFilterType: ColorFilterType) : Filter {

	override fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap? {
		val brightness = convertValueToMatchFilterTune(colorFilterType.brightness)
		val contrast = convertValueToMatchFilterTune(colorFilterType.contrast)
		val sharpness = convertValueToMatchFilterTune(colorFilterType.sharpness)

		val filtersList = listOf(
			FilterOperation(colorFilterType.scanbotFilter),
			TuneOperation(ImageFilterTuneType.BRIGHTNESS, brightness),
			TuneOperation(ImageFilterTuneType.CONTRAST, contrast),
			TuneOperation(ImageFilterTuneType.COMBINED_WHITE_BLACK_POINT, sharpness),
		)

		var processedBitmap: Bitmap? = null
		filtersList.forEach { filter ->
			processedBitmap = imageProcessor.processBitmap(processedBitmap ?: bitmap, filter)
		}

		return processedBitmap
	}

	private fun convertValueToMatchFilterTune(value: Int): Float {
		return ((value - 50) * 2).toFloat() / 100
	}
}