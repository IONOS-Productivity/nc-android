package com.strato.hidrive.common_ui.notification

import android.app.Notification
import android.os.Build
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

interface NotificationManager {

	fun createNotificationChannel()

	fun notificationBuilder(): NotificationCompat.Builder

	fun ongoingNotificationBuilder(): NotificationCompat.Builder

	fun showNotification(notification: Notification, notificationId: Int)

	fun cancelNotification(notificationId: Int)

	fun cancelAllNotifications()

	@RequiresApi(Build.VERSION_CODES.M)
	fun getActiveNotifications(): Array<StatusBarNotification>
}
