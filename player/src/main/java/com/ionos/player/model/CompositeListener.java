/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model;

import com.ionos.player.model.state.MultiplePlaybackState;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zuzik
 * Date: 7/17/16
 */
public class CompositeListener implements MultiplePlayer.Model.Listener {

	private final List<MultiplePlayer.Model.Listener> listeners = new ArrayList<>();

	public void addListener(MultiplePlayer.Model.Listener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeListener(MultiplePlayer.Model.Listener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void onUpdate(MultiplePlaybackState state) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).onUpdate(state);
		}
	}

	@Override
	public void onError(Throwable error) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).onError(error);
		}
	}

	@Override
	public void onSourceInfosChanged(List<PlayerFileInfo> originalSourceInfos, List<PlayerFileInfo> currentSourceInfos) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).onSourceInfosChanged(originalSourceInfos, currentSourceInfos);
		}
	}
}
