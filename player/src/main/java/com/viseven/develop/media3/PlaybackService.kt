package com.viseven.develop.media3

import android.content.Intent
import android.os.IBinder
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSessionService
import com.viseven.develop.media3.session.MediaSessionActivityFactory

class PlaybackService : MediaSessionService() {
	private val componentFactory by PlaybackServiceComponent.FactoryProvider
	private var mediaSession: MediaSession? = null
	private lateinit var mediaSessionActivityFactory: MediaSessionActivityFactory
	private var bindingCount: Int = 0

	@UnstableApi
	override fun onCreate() {
		super.onCreate()
		val component = componentFactory.create()
		mediaSession = component.provideMediaSession()
		mediaSessionActivityFactory = component.provideMediaSessionActivityFactory()
	}

	override fun onGetSession(controllerInfo: ControllerInfo): MediaSession {
		return mediaSession ?: throw IllegalStateException()
	}

	@UnstableApi
	override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
		val currentMediaId = session.player.currentMediaItem?.mediaId
		mediaSessionActivityFactory.create(currentMediaId)?.let(session::setSessionActivity)
		super.onUpdateNotification(session, startInForegroundRequired)
	}

	override fun onBind(intent: Intent?): IBinder? {
		val result = super.onBind(intent)
		if (result != null) {
			bindingCount++
		}
		return result
	}

	override fun onUnbind(intent: Intent?): Boolean {
		bindingCount--
		if (bindingCount == 0) {
			stopSelf()
		}
		return super.onUnbind(intent)
	}

	override fun onTaskRemoved(rootIntent: Intent?) {
		super.onTaskRemoved(rootIntent)
		release()
		stopSelf()
	}

	override fun onDestroy() {
		release()
		super.onDestroy()
	}

	private fun release() {
		mediaSession?.player?.release()
		mediaSession?.release()
		mediaSession = null
	}
}
