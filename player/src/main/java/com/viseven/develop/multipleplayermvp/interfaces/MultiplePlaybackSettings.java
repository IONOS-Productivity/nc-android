package com.viseven.develop.multipleplayermvp.interfaces;

/**
 * User: zuzik
 * Date: 7/7/16
 */
public interface MultiplePlaybackSettings<Mode> {

	RepeatMode getRepeatMode();

	boolean isRepeatSingle();

	void repeatSingle();

	void doNotRepeatSingle();

	boolean isShuffle();

	void shuffle();

	void doNotShuffle();

	Mode getMode();

	void setMode(Mode mode);

	enum RepeatMode {
		SINGLE,
		ALL,
		OFF,
	}
}
