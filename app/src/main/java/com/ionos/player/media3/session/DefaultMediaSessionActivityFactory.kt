package com.ionos.player.media3.session

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ionos.player.model.file_store.PlaybackFileStore
import com.ionos.player.model.predicate.IsVideoPredicate
import com.ionos.player.ui.IonosPlayerActivity
import com.ionos.player.util.SystemVersion
import javax.inject.Inject

class DefaultMediaSessionActivityFactory @Inject constructor(
	private val context: Context,
	private val playbackFileStore: PlaybackFileStore,
    private val isVideoPredicate: IsVideoPredicate,
) : MediaSessionActivityFactory {

	override fun create(currentMediaId: String?): PendingIntent? {
		val currentSourceInfo = currentMediaId?.let(playbackFileStore::getPlaybackFile) ?: return null

		val intent = if (isVideoPredicate.satisfied(currentSourceInfo)) {
			IonosPlayerActivity.createVideoPlayerIntent(context)
		} else {
			IonosPlayerActivity.createAudioPlayerIntent(context)
		}

		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

		val requestCode = System.currentTimeMillis().toInt()

		return if (SystemVersion.greaterOrEqualToS()) {
			PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
		} else {
			PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
		}
	}
}
