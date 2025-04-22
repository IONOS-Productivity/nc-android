package com.ionos.player.media3.session

import androidx.media3.session.MediaSession

interface MediaSessionHolder {

	fun init(session: MediaSession)

	fun get(): MediaSession?

	fun release()
}
