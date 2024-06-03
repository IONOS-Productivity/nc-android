package com.ionos.scanbot.screens.common.use_case.open_screen

import com.ionos.scanbot.filter.color.ColorFilterType

internal sealed interface OpenScreenIntent {
	val closeCurrent: Boolean

	data class OpenCameraScreenIntent(
		override val closeCurrent: Boolean,
	) : OpenScreenIntent

	data class OpenGalleryScreenIntent(
		val pictureId: String,
		override val closeCurrent: Boolean,
	) : OpenScreenIntent

	data class OpenCropScreenIntent(
		val pictureId: String,
		override val closeCurrent: Boolean,
	) : OpenScreenIntent

	data class OpenFilterScreenIntent(
		val pictureId: String,
		val filterType: ColorFilterType,
		override val closeCurrent: Boolean,
	) : OpenScreenIntent

	data class OpenRearrangeScreenIntent(
		override val closeCurrent: Boolean,
	) : OpenScreenIntent

	data class OpenSaveScreenIntent(
		override val closeCurrent: Boolean,
	) : OpenScreenIntent
}
