package com.ionos.scanbot.entity

import android.graphics.Bitmap
import com.ionos.scanbot.filter.FilterType
import com.ionos.scanbot.filter.Filterable
import com.ionos.scanbot.filter.color.ColorFilter
import com.ionos.scanbot.filter.crop.CropFilter
import com.ionos.scanbot.filter.rotate.RotateFilter
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 27.11.2017.
 */
internal data class OriginalPicture(
	val id: String,
	private val cropFilter: CropFilter,
	private val colorFilter: ColorFilter,
	private val rotateFilter: RotateFilter,
) : Filterable {

	override fun getCropFilter(): CropFilter = cropFilter

	override fun getColorFilter(): ColorFilter = colorFilter

	override fun getRotateFilter(): RotateFilter = rotateFilter

	override fun applyFilters(
		imageProcessor: ImageProcessor,
		filterTypes: Set<FilterType>,
		bitmap: Bitmap
	): Bitmap? {
		var bitmapWithFilters: Bitmap? = bitmap

		filterTypes.forEach { filterType ->
			when (filterType) {
				FilterType.COLOR -> {
					bitmapWithFilters = bitmapWithFilters?.let {
						colorFilter.apply(imageProcessor, it)
					}
				}
				FilterType.CROP -> {
					bitmapWithFilters = bitmapWithFilters?.let {
						cropFilter.apply(imageProcessor, it)
					}
				}
				FilterType.ROTATE -> {
					bitmapWithFilters = bitmapWithFilters?.let {
						rotateFilter.apply(imageProcessor, it)
					}
				}
			}
		}

		return bitmapWithFilters
	}
}