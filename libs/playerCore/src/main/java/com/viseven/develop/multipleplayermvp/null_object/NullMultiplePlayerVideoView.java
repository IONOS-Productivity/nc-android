package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;
import com.viseven.develop.player.interfaces.VideoViewSetter;

/**
 * User: zuzik
 * Date: 7/12/16
 */
public class NullMultiplePlayerVideoView<SourceInfo> implements MultiplePlayer.VideoView<SourceInfo> {

	private static final NullMultiplePlayerVideoView INSTANCE = new NullMultiplePlayerVideoView();

	public static <SourceInfo> NullMultiplePlayerVideoView<SourceInfo> getInstance() {
		return INSTANCE;
	}

	private NullMultiplePlayerVideoView() {
	}

	@Override
	public void setVideoViewAvailable() {

	}

	@Override
	public void setVideoViewUnavailable() {

	}

	@Override
	public void setVideoView(VideoViewSetter setter, SourceInfo sourceInfo) {

	}

	@Override
	public void clearVideoView(VideoViewSetter setter) {

	}
}
