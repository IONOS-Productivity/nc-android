package com.strato.hidrive.player.chromecast

import com.google.android.gms.cast.framework.CastContext

fun interface PlayerCastContextProvider {
	@Throws(IllegalStateException::class)
	fun requireContext(): CastContext
}