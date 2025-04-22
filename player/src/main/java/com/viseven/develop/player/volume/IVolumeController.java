package com.viseven.develop.player.volume;

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
