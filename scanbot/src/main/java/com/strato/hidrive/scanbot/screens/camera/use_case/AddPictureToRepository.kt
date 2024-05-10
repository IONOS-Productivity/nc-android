package com.strato.hidrive.scanbot.screens.camera.use_case

import android.graphics.Bitmap
import com.strato.hidrive.scanbot.provider.ContourDetectorProvider
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import com.strato.hidrive.scanbot.screens.common.use_case.SelectContour
import javax.inject.Inject

internal class AddPictureToRepository @Inject constructor(
	private val selectContour: SelectContour,
	private val contourDetectorProvider: ContourDetectorProvider,
	private val repositoryFacade: RepositoryFacade,
) {

	operator fun invoke(picture: Bitmap): String {
		val contourDetector = contourDetectorProvider.get()
		val selectedContour = selectContour(contourDetector, picture)
		return repositoryFacade.create(picture, selectedContour)
	}
}
