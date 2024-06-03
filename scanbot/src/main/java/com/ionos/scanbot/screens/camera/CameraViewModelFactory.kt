package com.ionos.scanbot.screens.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

internal class CameraViewModelFactory @Inject constructor(
	private val viewModel: Lazy<CameraViewModel>,
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return viewModel.get() as T
	}
}
