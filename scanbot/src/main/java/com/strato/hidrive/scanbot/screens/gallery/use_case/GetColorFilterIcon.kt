package com.strato.hidrive.scanbot.screens.gallery.use_case

import androidx.annotation.DrawableRes
import com.strato.hidrive.scanbot.R
import com.strato.hidrive.scanbot.filter.color.ColorFilterType
import com.strato.hidrive.scanbot.screens.gallery.GalleryScreen.ColorFilterIcon
import javax.inject.Inject

internal class GetColorFilterIcon @Inject constructor() {

	operator fun invoke(colorFilterType: ColorFilterType): ColorFilterIcon {
		return ColorFilterIcon(
			imageRes = getImageRes(colorFilterType),
			backgroundRes = getBackgroundRes(colorFilterType),
		)
	}

	@DrawableRes
	private fun getImageRes(colorFilterType: ColorFilterType): Int {
		return when (colorFilterType) {
			is ColorFilterType.BlackWhite -> R.drawable.scanbot_ic_filter_type_black_white
			is ColorFilterType.Color -> R.drawable.scanbot_ic_filter_type_color
			is ColorFilterType.Grayscale -> R.drawable.scanbot_ic_filter_type_grayscale
			is ColorFilterType.MagicColor -> R.drawable.scanbot_ic_filter_type_magic_color
			is ColorFilterType.MagicText -> R.drawable.scanbot_ic_filter_type_magic_text
			is ColorFilterType.None -> R.drawable.scanbot_ic_filter_type_none_gallery_screen
		}
	}

	@DrawableRes
	private fun getBackgroundRes(colorFilterType: ColorFilterType): Int {
		return when (colorFilterType) {
			is ColorFilterType.None -> 0
			else -> R.drawable.scanbot_gallery_filter_type_background
		}
	}
}
