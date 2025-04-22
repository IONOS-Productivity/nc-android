package com.viseven.develop.player.volume;

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
