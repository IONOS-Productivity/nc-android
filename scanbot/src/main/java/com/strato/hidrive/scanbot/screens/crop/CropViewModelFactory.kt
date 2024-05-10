package com.strato.hidrive.scanbot.screens.crop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.strato.hidrive.scanbot.provider.ContourDetectorProvider
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.screens.common.use_case.SelectContour
// import com.strato.hidrive.scanbot.tracking.ScanbotCropScreenEventTracker
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class CropViewModelFactory @AssistedInject constructor(
	@Assisted private val pictureId: String,
	private val selectContour: SelectContour,
	private val contourDetectorProvider: ContourDetectorProvider,
	private val repositoryFacade: RepositoryFacade,
	// private val eventTracker: ScanbotCropScreenEventTracker,
) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return create() as T
	}

	private fun create() = CropViewModel(
		pictureId,
		selectContour,
		contourDetectorProvider,
		repositoryFacade,
		// eventTracker,
	)

	@AssistedFactory
	interface Assistant {
		fun create(pictureId: String): CropViewModelFactory
	}
}
