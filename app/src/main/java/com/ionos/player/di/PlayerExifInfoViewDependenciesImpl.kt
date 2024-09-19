package com.ionos.player.di

import com.strato.hidrive.player.di.PlayerExifInfoViewDependencies
import javax.inject.Inject

class PlayerExifInfoViewDependenciesImpl @Inject constructor(
    override val showError: com.strato.hidrive.views.exif_info.ExifInfoShowError,
    override val tracker: com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker,
    override val pathToTitleTransformation: com.strato.hidrive.views.exif_info.transformation.PathToTitleTransformation,
    override val bytesToStringSizeTransformation: com.strato.hidrive.views.exif_info.transformation.BytesToStringSizeTransformation,
    override val millisToTimeDescriptionTransformation: com.strato.hidrive.views.exif_info.transformation.MillisToTimeDescriptionTransformation,
    override val millisToDateDescriptionTransformation: com.strato.hidrive.views.exif_info.transformation.MillisToDateDescriptionTransformation
) : PlayerExifInfoViewDependencies {
}