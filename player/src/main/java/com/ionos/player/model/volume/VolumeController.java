/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class VolumeController implements IVolumeController {

	private final AudioManager audioManager;
	private final Context context;
	private int maximumAudioPlayerVolume = 15;
	private static final int MAX_VOLUME_VALUE = 15;
	private double previousVolume = 15;
	private List<VolumeChangedListener> volumeChangedListeners = new ArrayList<>();
	private SettingsContentObserver settingsContentObserver;

	public VolumeController(Context context) {
		this.context = context;
		this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		if (audioManager != null) {
			this.maximumAudioPlayerVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		}
	}

	@Override
	public synchronized void setVolume(double volume) {
		this.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) Math.round(
				volume * this.maximumAudioPlayerVolume / MAX_VOLUME_VALUE), 0);

		int currentPlayerVolume = this.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		if (volume > 0) {
			this.previousVolume = Math.round(currentPlayerVolume * MAX_VOLUME_VALUE / this.maximumAudioPlayerVolume);
		}
	}

	@Override
	public synchronized double getCurrentVolume() {
		return Math.round(this.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MAX_VOLUME_VALUE / maximumAudioPlayerVolume);
	}

	@Override
	public double getPreviousVolume() {
		return previousVolume;
	}

	@Override
	public void startTrackingVolumeEvents() {
		this.context.registerReceiver(this.volumeChangedByUnplugReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
		this.settingsContentObserver = new SettingsContentObserver(this.context, new Handler());
		this.context.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, this.settingsContentObserver);

	}

	@Override
	public void stopTrackingVolumeEvents() {
		try {
			this.context.unregisterReceiver(this.volumeChangedByUnplugReceiver);
		} catch (IllegalArgumentException e) {
			Log.e(getClass().getSimpleName(), e.toString());
		}
		this.context.getContentResolver().unregisterContentObserver(settingsContentObserver);

	}

	private final BroadcastReceiver volumeChangedByUnplugReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			int currentPlayerVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			previousVolume = currentPlayerVolume == 0 ? previousVolume : currentPlayerVolume;
			notifyVolumeChangeListeners();
		}
	};

	@Override
	public void removeVolumeChangedListener(@NonNull VolumeChangedListener volumeChangedListener) {
		this.volumeChangedListeners.remove(volumeChangedListener);
	}

	@Override
	public void addVolumeChangedListener(@NonNull VolumeChangedListener volumeChangedListener) {
		if (!this.volumeChangedListeners.contains(volumeChangedListener)) {
			this.volumeChangedListeners.add(volumeChangedListener);
		}
	}

	private void notifyVolumeChangeListeners() {
		for (VolumeChangedListener volumeChangedListener : volumeChangedListeners) {
			volumeChangedListener.onVolumeChange(previousVolume);
		}
	}

	private class SettingsContentObserver extends ContentObserver {
		private double previousReceivedVolume;
		private Context context;

		public SettingsContentObserver(Context context, Handler handler) {
			super(handler);
			this.context = context;
			AudioManager audio = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
			this.previousReceivedVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		}

		//TODO move this class to presenter
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			double receivedVolumeValue = getCurrentVolume();
			if (receivedVolumeValue != this.previousReceivedVolume) {
				this.previousReceivedVolume = receivedVolumeValue;
				double currentVolume = getCurrentVolume();
				setVolume(currentVolume);
				notifyVolumeChangeListeners();
			}
		}
	}

}
