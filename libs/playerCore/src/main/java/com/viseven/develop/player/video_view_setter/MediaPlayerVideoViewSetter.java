package com.viseven.develop.player.video_view_setter;

import android.view.SurfaceHolder;

import com.viseven.develop.player.interfaces.ParamAction;
import com.viseven.develop.player.interfaces.VideoViewSetter;

import androidx.annotation.Nullable;

/**
 * User: zuzik
 * Date: 8/14/16
 */
public class MediaPlayerVideoViewSetter implements VideoViewSetter {

	private final ParamAction<SurfaceHolder> action;

	public MediaPlayerVideoViewSetter(ParamAction<SurfaceHolder> action) {
		this.action = action;
	}

	@Override
	public void setVideoView(@Nullable SurfaceHolder holder) {
		this.action.execute(holder);
	}
}
