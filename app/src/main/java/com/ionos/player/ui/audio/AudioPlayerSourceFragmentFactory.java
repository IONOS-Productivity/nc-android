package com.ionos.player.ui.audio;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.ui.pager.ViewPagerFragmentFactory;

import androidx.fragment.app.Fragment;

public class AudioPlayerSourceFragmentFactory implements ViewPagerFragmentFactory<PlaybackFile> {

	@Override
	public Fragment create(PlaybackFile item) {
		return AudioPlayerSourceFragment.createInstance(item);
	}
}
