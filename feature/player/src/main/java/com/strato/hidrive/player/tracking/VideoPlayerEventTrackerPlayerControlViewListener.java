package com.strato.hidrive.player.tracking;

import com.strato.hidrive.player.views.player.view.PlayerControlViewListener;

/**
 * Created by yaz on 1/18/17.
 */

public class VideoPlayerEventTrackerPlayerControlViewListener implements PlayerControlViewListener {

	private final VideoPlayerEventTracker eventTracker;

	public VideoPlayerEventTrackerPlayerControlViewListener(VideoPlayerEventTracker eventTracker) {
		this.eventTracker = eventTracker;
	}

	@Override
	public void onNextClicked() {
		this.eventTracker.trackPlayNext();
	}

	@Override
	public void onPreviousClicked() {
		this.eventTracker.trackPlayPrevious();
	}

	@Override
	public void onPlayClicked() {
		this.eventTracker.trackPlay();
	}

	@Override
	public void onPauseClicked() {
		this.eventTracker.trackPause();
	}

	@Override
	public void onRepeatClicked() {
		this.eventTracker.trackRepeat();
	}

	@Override
	public void onDoNotRepeatClicked() {
		this.eventTracker.trackDoNotRepeat();
	}

	@Override
	public void onShuffleClicked() {
		this.eventTracker.trackShuffle();
	}

	@Override
	public void onDoNotShuffleClicked() {
		this.eventTracker.trackDoNotShuffle();
	}

	@Override
	public void onProgressChangedByUser(int progress) {
		this.eventTracker.trackRewind();
	}

	@Override
	public void onProgressStopTrackingTouch() {
	}
}