package com.ionos.player.views.player.fragment;

import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.views.infinite_view_pager.ViewPagerFragmentFactory;

import androidx.fragment.app.Fragment;

/**
 * Created by Anton Shevchuk on 31.10.2016.
 */

public class VideoPlayerSourceFragmentFactory implements ViewPagerFragmentFactory<PlayerFileInfo> {

	@Override
	public Fragment create(PlayerFileInfo item) {
		return VideoPlayerSourceFragment.createInstance(item);
	}
}
