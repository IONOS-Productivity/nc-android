package com.ionos.exif_info.transformation

import com.strato.hidrive.views.exif_info.transformation.BytesToStringSizeTransformation
import javax.inject.Inject

class BytesToStringSizeTransformationImpl @Inject constructor(
): BytesToStringSizeTransformation {

    override fun transform(value: Long): String {
        return "$value b"
    }

}