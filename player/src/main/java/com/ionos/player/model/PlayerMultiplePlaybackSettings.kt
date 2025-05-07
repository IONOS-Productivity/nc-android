/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

abstract class PlayerMultiplePlaybackSettings(
	private val shouldRepeatAll: Boolean
): MultiplePlaybackSettings {

	override fun getRepeatMode(): MultiplePlaybackSettings.RepeatMode {
		return if (isRepeatSingle) {
			MultiplePlaybackSettings.RepeatMode.SINGLE
		} else if (this.shouldRepeatAll) {
			MultiplePlaybackSettings.RepeatMode.ALL
		} else {
			MultiplePlaybackSettings.RepeatMode.OFF
		}
	}
}