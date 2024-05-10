package com.strato.hidrive.common_ui.image.loader

sealed interface ImageOverlay {

	val resId: Int

	data class PercentageImageOverlay(
		override val resId: Int,
		val percent: Float,
	) : ImageOverlay

	data class ImageLoaderOverlay(
		override val resId: Int,
		val size: Int,
	) : ImageOverlay{
		val width = size
		val height = size
	}

	object None : ImageOverlay {
		override val resId: Int
			get() = 0
	}
}