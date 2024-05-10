package com.strato.hidrive.scanbot.screens.camera.use_case.import_pictures

import android.net.Uri

internal sealed interface ImportPicturesInputItem {

	data class Picture(val uri: Uri) : ImportPicturesInputItem

	object CancellationSignal : ImportPicturesInputItem
}
