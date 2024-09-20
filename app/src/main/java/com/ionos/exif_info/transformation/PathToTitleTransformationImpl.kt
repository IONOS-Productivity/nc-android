package com.ionos.exif_info.transformation

import com.strato.hidrive.views.exif_info.transformation.PathToTitleTransformation
import javax.inject.Inject

class PathToTitleTransformationImpl @Inject constructor(
) : PathToTitleTransformation {

    override fun transform(value: String): String {
        return value
    }

}