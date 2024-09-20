package com.ionos.exif_info.transformation

import com.strato.hidrive.views.exif_info.transformation.MillisToDateDescriptionTransformation
import javax.inject.Inject

class MillisToDateDescriptionTransformationImpl @Inject constructor(
) : MillisToDateDescriptionTransformation {

    override fun transform(value: Long): String {
        return "$value millis"
    }
}