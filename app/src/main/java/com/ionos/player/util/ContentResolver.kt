package com.ionos.player.util

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun ContentResolver.observeContentChanges(uri: Uri, notifyForDescendants: Boolean) = callbackFlow {
	val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
		override fun onChange(selfChange: Boolean) {
			trySend(selfChange)
		}
	}
	registerContentObserver(uri, notifyForDescendants, contentObserver)
	awaitClose { unregisterContentObserver(contentObserver) }
}
