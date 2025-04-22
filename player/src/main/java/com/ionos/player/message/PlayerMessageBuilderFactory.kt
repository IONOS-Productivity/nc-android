package com.ionos.player.message

import android.content.Context

interface PlayerMessageBuilderFactory {
	fun show(context: Context, message: String?)
}