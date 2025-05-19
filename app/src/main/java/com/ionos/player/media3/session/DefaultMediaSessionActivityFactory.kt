package com.ionos.player.media3.session

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ionos.player.model.PlaybackFileType
import com.ionos.player.model.file_store.PlaybackFileStore
import com.ionos.player.ui.PlayerActivity
import com.ionos.player.util.SystemVersion
import javax.inject.Inject

class DefaultMediaSessionActivityFactory @Inject constructor(
	private val context: Context,
	private val playbackFileStore: PlaybackFileStore,
) : MediaSessionActivityFactory {

	override fun create(currentMediaId: String?): PendingIntent? {
		val currentFile = currentMediaId?.let(playbackFileStore::getFile) ?: return null
		val fileType = PlaybackFileType.entries
			.firstOrNull { currentFile.mimeType.startsWith(it.value, ignoreCase = true) }
			?: throw IllegalArgumentException("Unsupported file type: ${currentFile.mimeType}")

		val intent = PlayerActivity.createIntent(context, fileType)
			.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

		val requestCode = System.currentTimeMillis().toInt()

		return if (SystemVersion.greaterOrEqualToS()) {
			PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
		} else {
			PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
		}
	}
}
