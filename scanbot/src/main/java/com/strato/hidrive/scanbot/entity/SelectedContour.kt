package com.strato.hidrive.scanbot.entity

import android.graphics.PointF

/**
 * Created by: Alex Kucherenko
 * Date: 23.11.2017.
 */

//TODO alk - make the class internal
 data class SelectedContour(val normalizedPolygon: List<PointF>) {
	companion object {
		val DEFAULT: SelectedContour = SelectedContour(
			normalizedPolygon = listOf(
				PointF(0.0F, 0.0F),
				PointF(1.0F, 0.0F),
				PointF(1.0F, 1.0F),
				PointF(0.0F, 1.0F),
			),
		)
	}
}