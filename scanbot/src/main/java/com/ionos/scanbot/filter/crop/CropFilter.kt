/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.filter.crop

import android.graphics.Bitmap
import com.ionos.scanbot.entity.SelectedContour
import com.ionos.scanbot.filter.Filter
import io.scanbot.sdk.process.CropOperation
import io.scanbot.sdk.process.ImageProcessor


internal data class CropFilter(val contour: SelectedContour) : Filter {

	override fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap? {
		return imageProcessor.processBitmap(bitmap, listOf(CropOperation(contour.normalizedPolygon)))
	}
}