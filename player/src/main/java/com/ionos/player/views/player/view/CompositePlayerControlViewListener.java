package com.ionos.player.views.player.view;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yaz on 1/18/17.
 */

public class CompositePlayerControlViewListener implements PlayerControlViewListener {

	private final Set<PlayerControlViewListener> listeners = new HashSet<>();

	public void addListener(PlayerControlViewListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(PlayerControlViewListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void onNextClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onNextClicked();
		}
	}

	@Override
	public void onPreviousClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onPreviousClicked();
		}
	}

	@Override
	public void onPlayClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onPlayClicked();
		}
	}

	@Override
	public void onPauseClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onPauseClicked();
		}
	}

	@Override
	public void onRepeatClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onRepeatClicked();
		}
	}

	@Override
	public void onDoNotRepeatClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onDoNotRepeatClicked();
		}
	}

	@Override
	public void onShuffleClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onShuffleClicked();
		}
	}

	@Override
	public void onDoNotShuffleClicked() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onDoNotShuffleClicked();
		}
	}

	@Override
	public void onProgressChangedByUser(int progress) {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onProgressChangedByUser(progress);
		}
	}

	@Override
	public void onProgressStopTrackingTouch() {
		for (PlayerControlViewListener listener : this.listeners) {
			listener.onProgressStopTrackingTouch();
		}
	}
}
