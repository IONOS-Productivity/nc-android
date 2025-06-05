package com.ionos.player.media3.session

import androidx.media3.session.MediaSession

interface MediaSessionHolder {

	fun getMediaSession(): MediaSession

	fun release()
}
