package com.strato.hidrive.player.message

import android.content.Context

interface PlayerMessageBuilderFactory {
	fun show(context: Context, message: String?)
}