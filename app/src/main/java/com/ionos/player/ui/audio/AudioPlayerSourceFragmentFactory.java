/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

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
