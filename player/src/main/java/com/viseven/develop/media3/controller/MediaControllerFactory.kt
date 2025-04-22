package com.viseven.develop.media3.controller

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.viseven.develop.media3.PlaybackService

class MediaControllerFactory(
	private val controllerListener: MediaController.Listener,
) {

	fun create(context: Context): ListenableFuture<MediaController> {
		val token = SessionToken(context, ComponentName(context, PlaybackService::class.java))
		return MediaController
			.Builder(context, token)
			.setListener(controllerListener)
			.buildAsync()
	}
}
