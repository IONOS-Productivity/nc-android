package com.ionos.scanbot.filter

import android.graphics.Bitmap
import io.scanbot.sdk.process.ImageProcessor

/**
 * Created by: Alex Kucherenko
 * Date: 27.11.2017.
 */
internal interface Filterable {

	fun getCropFilter(): Filter

	fun getColorFilter(): Filter

	fun getRotateFilter(): Filter

	fun applyFilters(imageProcessor: ImageProcessor, filterTypes: Set<FilterType>, bitmap: Bitmap): Bitmap?
}