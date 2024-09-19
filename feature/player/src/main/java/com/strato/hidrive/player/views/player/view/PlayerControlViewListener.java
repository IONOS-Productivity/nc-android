package com.strato.hidrive.player.views.player.view;

/**
 * Created by yaz on 1/18/17.
 */

public interface PlayerControlViewListener {

	void onNextClicked();

	void onPreviousClicked();

	void onPlayClicked();

	void onPauseClicked();

	void onRepeatClicked();

	void onDoNotRepeatClicked();

	void onShuffleClicked();

	void onDoNotShuffleClicked();

	void onProgressChangedByUser(int progress);

	void onProgressStopTrackingTouch();
}
