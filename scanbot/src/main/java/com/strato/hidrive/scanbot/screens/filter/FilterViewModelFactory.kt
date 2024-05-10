package com.strato.hidrive.scanbot.screens.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.strato.hidrive.scanbot.provider.SdkProvider
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.screens.filter.FilterScreen.State
// import com.strato.hidrive.scanbot.tracking.ScanbotFilterScreenEventTracker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class FilterViewModelFactory @AssistedInject constructor(
	@Assisted private val initialState: State,
	private val sdkProvider: SdkProvider,
	private val repositoryFacade: RepositoryFacade,
	// private val eventTracker: ScanbotFilterScreenEventTracker,
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return create() as T
	}

	private fun create(): FilterViewModel = FilterViewModel(
		initialState,
		repositoryFacade,
		sdkProvider.get().imageProcessor(),
		// eventTracker,
	)

	@AssistedFactory
	interface Assistant {
		fun create(initialState: State): FilterViewModelFactory
	}
}