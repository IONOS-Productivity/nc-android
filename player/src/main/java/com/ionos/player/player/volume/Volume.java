/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.volume;

import android.app.Activity;
import android.media.AudioManager;

/**
 * User: zuzik
 * Date: 6/11/16
 */
public class Volume {
	public void useVolumeKeysToControlPlaybackVolume(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
}
