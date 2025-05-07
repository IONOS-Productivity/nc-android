package com.ionos.player.ui.message

import android.content.Context

interface PlayerMessageBuilderFactory {
	fun show(context: Context, message: String?)
}