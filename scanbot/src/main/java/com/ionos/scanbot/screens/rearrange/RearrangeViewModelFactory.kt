package com.ionos.scanbot.screens.rearrange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import javax.inject.Inject

internal class RearrangeViewModelFactory @Inject constructor(
	private val viewModel: Lazy<RearrangeViewModel>,
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return viewModel.get() as T
	}
}
