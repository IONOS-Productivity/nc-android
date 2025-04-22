/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.player.volume;

import androidx.annotation.NonNull;

public interface IVolumeController {
	void setVolume(double volume);

	double getCurrentVolume();

	double getPreviousVolume();

	void startTrackingVolumeEvents();

	void stopTrackingVolumeEvents();

	void removeVolumeChangedListener(@NonNull VolumeChangedListener volumeChangedListener);

	void addVolumeChangedListener(@NonNull VolumeChangedListener volumeChangedListener);
}
