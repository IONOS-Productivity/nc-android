package com.ionos.player.model

import android.net.Uri
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.utils.MimeTypeUtil

const val OC_FILE_SCHEME = "OCFile"

fun OCFile.toPlayerFileInfo() = PlayerFileInfo(
    id = localId.toString(),
    uri = getUri().toString(),
    name = fileName.substringBeforeLast("."),
    mimeType = mimeType,
    contentLength = fileLength,
    lastModified = modificationTimestamp,
)

fun OCFile.getNeighborFilesTypes() = when {
    MimeTypeUtil.isAudio(this) -> NeighborFilesTypes.AUDIO
    MimeTypeUtil.isVideo(this) -> NeighborFilesTypes.VIDEO
    else -> NeighborFilesTypes.FILES
}

fun OCFile.getUri(): Uri {
    return Uri.Builder()
        .scheme(OC_FILE_SCHEME)
        .authority("")
        .appendPath(localId.toString())
        .build()
}

fun Uri.getOCFileId(): Long? {
    return scheme
        ?.takeIf { it == OC_FILE_SCHEME }
        ?.let { pathSegments.firstOrNull()?.toLongOrNull() }
}
