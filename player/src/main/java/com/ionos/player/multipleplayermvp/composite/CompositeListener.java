/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.multipleplayermvp.composite;

import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zuzik
 * Date: 7/17/16
 */
public class CompositeListener<SourceInfo> implements MultiplePlayer.Model.Listener<SourceInfo> {

	private final List<MultiplePlayer.Model.Listener<SourceInfo>> listeners = new ArrayList<>();

	public void addListener(MultiplePlayer.Model.Listener<SourceInfo> listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeListener(MultiplePlayer.Model.Listener<SourceInfo> listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void onUpdate(MultiplePlaybackState<SourceInfo> state) {
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
	public void onSourceInfosChanged(List<SourceInfo> originalSourceInfos, List<SourceInfo> currentSourceInfos) {
		for (int i = 0; i < this.listeners.size(); i++) {
			this.listeners.get(i).onSourceInfosChanged(originalSourceInfos, currentSourceInfos);
		}
	}
}
