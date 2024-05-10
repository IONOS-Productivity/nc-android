package com.strato.hidrive.common_ui.notification;

public final class NotificationIds {
	public static final int LOADING_GENERAL_INFO = 0;
	public static final int LOADING_PROGRESS = 1;
	public static final int USER_SPACE = 2;
	public static final int GROUP_SUMMARY = 3;
	public static final int PLAYER = 4;
	public static final int MIGRATION = 5;
	public static final int BACKGROUND_WORKER_FINAL = 7;
	public static final int BACKGROUND_WORKER_PROGRESS = 8;
	public static final int PROGRESS_DISPLAY_NOTIFICATION = 9;
	public static final int FILE_PICKER_BACKGROUND_WORKER_PROGRESS = 10;
	public static final int FILE_PICKER_BACKGROUND_WORKER_FINAL = 11;

	private static int lastNotificationId = 11;

	private NotificationIds() {
		throw new AssertionError();
	}

	public static int generateNotificationId() {
		return ++lastNotificationId;
	}
}
