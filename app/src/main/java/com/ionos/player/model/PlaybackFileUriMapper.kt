package com.ionos.player.model

import android.net.Uri
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.lib.resources.shares.OCShare

const val REMOTE_FILE_SCHEME = "remoteFile"

fun OCFile.getPlaybackUri(): Uri {
	return getPlaybackUri(localId)
}

fun OCShare.getPlaybackUri(): Uri {
	return getPlaybackUri(fileSource)
}

fun getPlaybackUri(fileId: Long): Uri {
	return Uri.Builder()
		.scheme(REMOTE_FILE_SCHEME)
		.authority("")
		.appendPath(fileId.toString())
		.build()
}

fun Uri.getRemoteFileId(): Long? {
	return scheme
		?.takeIf { it == REMOTE_FILE_SCHEME }
		?.let { pathSegments.firstOrNull()?.toLongOrNull() }
}
