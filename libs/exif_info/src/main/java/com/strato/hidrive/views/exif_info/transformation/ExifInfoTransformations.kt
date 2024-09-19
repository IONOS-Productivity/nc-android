package com.strato.hidrive.views.exif_info.transformation

class ExifInfoTransformations @JvmOverloads constructor(
	val pathToTitleTransformation: PathToTitleTransformation = PathToTitleTransformation { it },
	val bytesToStringSizeTransformation: BytesToStringSizeTransformation = BytesToStringSizeTransformation { "$it b" },
	val millisToTimeDescriptionTransformation: MillisToTimeDescriptionTransformation = MillisToTimeDescriptionTransformation { "$it ms" },
	val millisToDateDescriptionTransformation: MillisToDateDescriptionTransformation = MillisToDateDescriptionTransformation { "$it ms" },
)
