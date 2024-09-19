package com.strato.hidrive.views.exif_info

import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker
import com.strato.hidrive.views.exif_info.transformation.BytesToStringSizeTransformation
import com.strato.hidrive.views.exif_info.transformation.ExifInfoTransformations
import com.strato.hidrive.views.exif_info.transformation.MillisToDateDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.MillisToTimeDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.PathToTitleTransformation

interface ExifInfoViewDependencies{

	val showError: ExifInfoShowError
	val tracker: ExifInfoEventTracker

	val pathToTitleTransformation: PathToTitleTransformation
	val bytesToStringSizeTransformation: BytesToStringSizeTransformation
	val millisToTimeDescriptionTransformation: MillisToTimeDescriptionTransformation
	val millisToDateDescriptionTransformation: MillisToDateDescriptionTransformation

	fun inject(exifInfoView: ExifInfoView){
		exifInfoView.setExifInfoShowError(showError)
		exifInfoView.setTracker(tracker)
		exifInfoView.setTransformations(
			ExifInfoTransformations(
				pathToTitleTransformation,
				bytesToStringSizeTransformation,
				millisToTimeDescriptionTransformation,
				millisToDateDescriptionTransformation,
			)
		)
	}

}