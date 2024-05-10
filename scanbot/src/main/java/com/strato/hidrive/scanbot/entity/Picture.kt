package com.strato.hidrive.scanbot.entity

import com.strato.hidrive.scanbot.filter.color.ColorFilter
import com.strato.hidrive.scanbot.filter.color.ColorFilterType
import com.strato.hidrive.scanbot.filter.crop.CropFilter
import com.strato.hidrive.scanbot.filter.rotate.RotateFilter

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */

//TODO alk - make the class internal

 data class Picture(
	val id: String,
	val original: OriginalPicture,
	val modified: ModifiedPicture,
) {

	fun makeCopy(contour: SelectedContour) = copy(
		original = this.original.copy(cropFilter = CropFilter(contour)),
	)

	fun makeCopy(colorFilterType: ColorFilterType) = copy(
		original = this.original.copy(colorFilter = ColorFilter(colorFilterType)),
	)

	fun makeCopy(rotateDegrees: Float) = copy(
		original = this.original.copy(rotateFilter = RotateFilter(degrees = rotateDegrees)),
	)
}