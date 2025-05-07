package com.ionos.player.model.image_loader

data class PlayerImageLoaderOptions(
	val scaleType: ScaleType,
) {

	enum class ScaleType {
		CENTER_CROP,
		CENTER_INSIDE
	}

}