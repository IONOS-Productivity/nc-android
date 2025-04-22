package com.viseven.develop.media3.session

import android.app.PendingIntent

interface MediaSessionActivityFactory {
	fun create(currentMediaId: String?): PendingIntent?
}
