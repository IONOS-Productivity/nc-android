package com.viseven.develop.multipleplayermvp.composite;

import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zuzik
 * Date: 7/17/16
 */
public class CompositeListener<SourceInfo, Mode> implements MultiplePlayer.Model.Listener<SourceInfo, Mode> {

	private final List<MultiplePlayer.Model.Listener<SourceInfo, Mode>> listeners = new ArrayList<>();

	public void addListener(MultiplePlayer.Model.Listener<SourceInfo, Mode> listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeListener(MultiplePlayer.Model.Listener<SourceInfo, Mode> listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void onUpdate(MultiplePlaybackState<SourceInfo, Mode> state) {
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
