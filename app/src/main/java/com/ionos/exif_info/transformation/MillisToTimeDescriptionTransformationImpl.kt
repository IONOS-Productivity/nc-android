package com.ionos.exif_info.transformation

import com.strato.hidrive.views.exif_info.transformation.MillisToTimeDescriptionTransformation
import javax.inject.Inject

class MillisToTimeDescriptionTransformationImpl @Inject constructor(
) : MillisToTimeDescriptionTransformation {

    override fun transform(value: Long): String {
        return "$value millis"
    }

}