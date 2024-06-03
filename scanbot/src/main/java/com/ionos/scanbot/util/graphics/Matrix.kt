package com.ionos.scanbot.util.graphics

import android.graphics.Matrix
import android.graphics.PointF

internal fun <T> T.toRotateMatrix(): Matrix? where T : Number, T : Comparable<T> = when {
	toFloat() > 0 -> Matrix().apply { postRotate(toFloat()) }
	else -> null
}

internal fun Matrix.mapPoints(points: List<PointF>): List<PointF> = points
	.map { mapPoint(it) }

internal fun Matrix.mapPoint(point: PointF): PointF = floatArrayOf(point.x, point.y).let {
	mapPoints(it)
	return PointF(it[0], it[1])
}
