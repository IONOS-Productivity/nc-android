package com.ionos.scanbot.screens.filter.view

import com.ionos.scanbot.filter.color.ColorFilterType

internal interface FilterControlsListener {

	fun onFilterTypeChanged(filterType: ColorFilterType)

	fun onBrightnessChanged(filterType: ColorFilterType)

	fun onSharpnessChanged(filterType: ColorFilterType)

	fun onContrastChanged(filterType: ColorFilterType)
}
