package com.ionos.player.model

import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.lib.resources.shares.OCShare
import com.owncloud.android.utils.MimeTypeUtil
import java.io.File

fun OCFile.toPlaybackFile() = PlaybackFile(
    id = localId.toString(),
    uri = getPlaybackUri().toString(),
    name = fileName.substringBeforeLast("."),
    mimeType = mimeType,
    contentLength = fileLength,
    lastModified = modificationTimestamp,
)

fun OCShare.toPlaybackFile() = PlaybackFile(
    id = fileSource.toString(),
    uri = getPlaybackUri().toString(),
    name = path?.let { File(it).name.substringBeforeLast(".") } ?: "",
    mimeType = getMimeType(),
    contentLength = -1L,
    lastModified = -1L,
)

private fun OCShare.getMimeType(): String {
    return mimetype
        ?.takeIf { it.isNotEmpty() }
        ?: path?.let { MimeTypeUtil.getMimeTypeFromPath(it) }
        ?: ""
}
