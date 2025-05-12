package com.ionos.player.media3.session

import androidx.media3.session.MediaSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaSessionHolder @Inject constructor() {
	private var session: MediaSession? = null

	fun init(session: MediaSession) {
		this.session = session
	}

	fun get(): MediaSession? {
		return session
	}

	fun release() {
		session?.let {
			this.session = null
			it.player.release()
			it.release()
		}
	}
}
