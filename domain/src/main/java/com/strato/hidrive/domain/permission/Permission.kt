package com.strato.hidrive.domain.permission

import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

enum class Permission(
	val id: String,
	val isMandatory: Boolean = true,
	val requestRestriction: RequestPermissionRestriction = RequestPermissionRestriction.None,
) {

	ACCESS_CAMERA(
		id = "access_camera_permission",
	),

	ACCESS_CALENDAR(
		id = "access_calendar_permission",
	),

	ACCESS_CONTACTS(
		id = "access_contacts_permission",
	),

	ACCESS_AUDIO(
		id = "access_audio_permission",
	),

	ACCESS_IMAGES(
		id = "access_images_permission",
	),

	ACCESS_VIDEO(
		id = "access_video_permission",
	),

	PICK_FILES(
		id = "pick_files_permission",
	),

	SAVE_FILES(
		id = "save_files_permission",
	),

	SHOW_NOTIFICATIONS(
		id = "show_notifications_permission",
		isMandatory = false,
		requestRestriction = RequestPermissionRestriction.OncePerPeriod(period = 30.days),
	),
}
