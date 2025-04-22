package com.ionos.player.error

import com.google.android.gms.cast.MediaError

class ChromecastMediaError(
	private val mediaError: MediaError,
) : Exception(mediaError.reason)
