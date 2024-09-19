package com.viseven.develop.multipleplayermvp.null_object;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * Created by yaz on 9/23/16.
 */

public class NullMultiplePlayerVideoPresenter<SourceInfo> implements MultiplePlayer.VideoPresenter<SourceInfo> {

	private static final NullMultiplePlayerVideoPresenter INSTANCE = new NullMultiplePlayerVideoPresenter();

	public static <SourceInfo> NullMultiplePlayerVideoPresenter<SourceInfo> getInstance() {
		return INSTANCE;
	}

	@Override
	public void setView(MultiplePlayer.VideoView<SourceInfo> view) {
	}

	@Override
	public void onVideoViewCreated() {
	}

	@Override
	public void onVideoViewDestroyed() {
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onAppear() {
	}

	@Override
	public void onDisappear() {
	}
}
