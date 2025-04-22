package com.ionos.player.media3.session

import androidx.media3.session.MediaSession

interface MediaSessionFactory {
	fun create(): MediaSession
}
