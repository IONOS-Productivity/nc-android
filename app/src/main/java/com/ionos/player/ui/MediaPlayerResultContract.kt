package com.ionos.player.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.ionos.player.model.NeighborFilesTypes
import com.owncloud.android.datamodel.OCFile

class MediaPlayerResultContract :
	ActivityResultContract<MediaPlayerResultContract.Input, OCFile?>() {

	data class Input(
		val filesTypes: NeighborFilesTypes,
		// val sourceMode: FileSourceMode,
	)

	private val typeError = IllegalArgumentException("Supports only audion or video")

	override fun createIntent(context: Context, input: Input): Intent {
		return with(input) {
			when (filesTypes) {
				NeighborFilesTypes.AUDIO -> IonosPlayerActivity.createAudioPlayerIntent(
					context,
					// sourceMode
				)

				NeighborFilesTypes.VIDEO -> IonosPlayerActivity.createVideoPlayerIntent(
					context,
					// sourceMode
				)

				else -> null
			}
		} ?: throw typeError
	}

	override fun parseResult(resultCode: Int, intent: Intent?): OCFile? {
		return if (resultCode == Activity.RESULT_OK) IonosPlayerActivity.parseResultIntent(intent)
		else null
	}

}