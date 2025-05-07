package com.ionos.player.ui.audio;

import com.ionos.player.model.PlayerFileInfo;
import com.ionos.player.ui.pager.ViewPagerFragmentFactory;

import androidx.fragment.app.Fragment;

/**
 * Created by Anton Shevchuk on 31.10.2016.
 */

public class AudioPlayerSourceFragmentFactory implements ViewPagerFragmentFactory<PlayerFileInfo> {

	@Override
	public Fragment create(PlayerFileInfo item) {
		return AudioPlayerSourceFragment.createInstance(item);
	}
}
