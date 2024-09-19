package com.strato.hidrive.player.image_loading

data class PlayerImageLoaderOptions(
	val scaleType: ScaleType,
) {

	enum class ScaleType {
		CENTER_CROP,
		CENTER_INSIDE
	}

}