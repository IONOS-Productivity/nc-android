package com.ionos.player.ui.control.listener;

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
