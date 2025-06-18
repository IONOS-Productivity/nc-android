package com.ionos.player.ui.video;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.ui.pager.ViewPagerFragmentFactory;

import androidx.fragment.app.Fragment;

public class VideoPlayerSourceFragmentFactory implements ViewPagerFragmentFactory<PlaybackFile> {

	@Override
	public Fragment create(PlaybackFile item) {
		return VideoPlayerSourceFragment.createInstance(item);
	}
}
