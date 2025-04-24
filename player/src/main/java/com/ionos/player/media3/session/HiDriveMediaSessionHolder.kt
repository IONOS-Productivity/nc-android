package com.ionos.player.media3.session

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(UnstableApi::class)
class HiDriveMediaSessionHolder @Inject constructor() : MediaSessionHolder {
	private var session: MediaSession? = null

	override fun init(session: MediaSession) {
		this.session = session
	}

	override fun get(): MediaSession? {
		return session
	}

	override fun release() {
		session?.let {
			this.session = null
			it.player.release()
			it.release()
		}
	}
}
