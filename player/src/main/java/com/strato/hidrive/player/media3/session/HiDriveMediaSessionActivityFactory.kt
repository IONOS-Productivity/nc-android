package com.strato.hidrive.player.media3.session

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.strato.hidrive.player.domain.PlayerFileInfo
import com.strato.hidrive.player.transformation.FileInfoToIntentTransformation
import com.strato.hidrive.player.util.SystemVersion
import com.viseven.develop.media3.session.MediaSessionActivityFactory
import com.viseven.develop.media3.store.SourceInfoStore

class HiDriveMediaSessionActivityFactory(
	private val context: Context,
	private val sourceInfoStore: SourceInfoStore<PlayerFileInfo>,
	private val fileInfoToIntentTransformation: FileInfoToIntentTransformation,
) : MediaSessionActivityFactory {

	override fun create(currentMediaId: String?): PendingIntent? {
		val currentSourceInfo = currentMediaId?.let(sourceInfoStore::getSourceInfo) ?: return null

		val flag = if (SystemVersion.greaterOrEqualToS()) {
			PendingIntent.FLAG_IMMUTABLE
		} else {
			PendingIntent.FLAG_UPDATE_CURRENT
		}

        return fileInfoToIntentTransformation.transform(currentSourceInfo)
            ?.run {
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                PendingIntent.getActivity(context, System.currentTimeMillis().toInt(), this, flag)
            }
	}
}
