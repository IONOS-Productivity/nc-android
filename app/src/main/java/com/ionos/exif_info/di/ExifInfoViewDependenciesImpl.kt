package com.ionos.exif_info.di

import com.strato.hidrive.views.exif_info.ExifInfoShowError
import com.strato.hidrive.views.exif_info.ExifInfoViewDependencies
import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker
import com.strato.hidrive.views.exif_info.transformation.BytesToStringSizeTransformation
import com.strato.hidrive.views.exif_info.transformation.MillisToDateDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.MillisToTimeDescriptionTransformation
import com.strato.hidrive.views.exif_info.transformation.PathToTitleTransformation
import javax.inject.Inject

class ExifInfoViewDependenciesImpl @Inject constructor(
	override val showError: ExifInfoShowError,
	override val tracker: ExifInfoEventTracker,
	override val pathToTitleTransformation: PathToTitleTransformation,
	override val bytesToStringSizeTransformation: BytesToStringSizeTransformation,
	override val millisToTimeDescriptionTransformation: MillisToTimeDescriptionTransformation,
	override val millisToDateDescriptionTransformation: MillisToDateDescriptionTransformation,
) : ExifInfoViewDependencies