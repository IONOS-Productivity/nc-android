package com.strato.hidrive.player.views.player.fragment;

import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.views.infinite_view_pager.ViewPagerFragmentFactory;

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
