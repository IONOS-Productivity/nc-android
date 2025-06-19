/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.VideoViewSetter;
import com.ionos.player.model.state.VideoSize;
import com.ionos.player.ui.MultiplePlayer;
import com.ionos.player.ui.video.surface.PlayerCompatible;
import com.ionos.player.ui.video.surface.SurfaceInvalidator;
import com.ionos.player.ui.video.surface.SurfaceVideoView;
import com.ionos.player.util.ScreenUtils;
import com.ionos.player.util.SystemVersion;
import com.owncloud.android.R;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public class VideoPlayerSourceFragment extends Fragment {

	private final static String ARGUMENT_FILE = "ARGUMENT_FILE";

	@Inject
    PlaybackModel playerModel;

	private PlaybackFile file;
	private MultiplePlayer.VideoPresenter videoPresenter;

	private SurfaceView surfaceView;
	private SurfaceVideoView surfaceVideoView;
	private VideoSize previousVideoSize;

	public static Fragment createInstance(PlaybackFile file) {
		VideoPlayerSourceFragment fragment = new VideoPlayerSourceFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARGUMENT_FILE, file);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidSupportInjection.inject(this);
		this.file = (PlaybackFile) getArguments().getSerializable(ARGUMENT_FILE);
		this.videoPresenter = new MultiplePlayerVideoPresenter(this.playerModel, this.file);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.player_video_source_fragment, container, false);
		this.surfaceView = content.findViewById(R.id.surfaceView);
		MultiplePlayerVideoPresenter surfaceVideoViewPresenter = new MultiplePlayerVideoPresenter(this.playerModel, this.file);
		this.surfaceVideoView = new SurfaceVideoView(this.surfaceView, surfaceVideoViewPresenter);
		surfaceVideoViewPresenter.setView(this.surfaceVideoView);
		return content;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.videoPresenter.setView(this.videoView);
		this.videoPresenter.onCreate();
		this.surfaceVideoView.onCreate();
	}

	@Override
	public void onDestroyView() {
		this.videoPresenter.setView(null);
		this.videoPresenter.onDestroy();
		this.surfaceVideoView.onDestroy();
		super.onDestroyView();
	}

	@Override
	public void onStart() {
		super.onStart();
		this.videoPresenter.onAppear();
		this.surfaceVideoView.onAppear();

		SurfaceInvalidator surfaceInvalidator = getSurfaceInvalidator();
		if (surfaceInvalidator != null) {
			if (surfaceView != null) {
				surfaceInvalidator.addSurface(surfaceView);
			}
			surfaceInvalidator.invalidateSurfaceViews();
		}
	}

	@Nullable
	private SurfaceInvalidator getSurfaceInvalidator() {
		Activity activity = getActivity();
		if (activity instanceof PlayerCompatible) {
			if (SystemVersion.greaterOrEqualToR()) {
				return ((PlayerCompatible) activity).getSurfaceInvalidator();
			}
		}
		return null;
	}

	@Override
	public void onStop() {
		this.videoPresenter.onDisappear();
		this.surfaceVideoView.onDisappear();

		SurfaceInvalidator surfaceInvalidator = getSurfaceInvalidator();
		if (surfaceInvalidator != null && surfaceView != null) {
			surfaceInvalidator.removeSurface(surfaceView);
		}

		super.onStop();
	}

	private final MultiplePlayer.VideoView videoView = new MultiplePlayer.VideoView() {
		@Override
		public void setVideoViewAvailable() {
            surfaceView.setVisibility(View.VISIBLE);
            final VideoSize videoSize = playerModel.getState().get().currentItemState.get().videoSize;
            if (videoSize != null && !Objects.equals(previousVideoSize, videoSize)) {
                previousVideoSize = videoSize;
                setVideoSize(videoSize.getWidth(), videoSize.getHeight());
            }
		}

		@Override
		public void setVideoViewUnavailable() {
			surfaceView.setVisibility(View.INVISIBLE);
		}

		@Override
		public void setVideoView(VideoViewSetter videoViewSetter, PlaybackFile file) {
		}

		@Override
		public void clearVideoView(VideoViewSetter setter) {
		}
	};

	private void setVideoSize(int videoWidth, int videoHeight) {
		float videoProportion = (float) videoWidth / (float) videoHeight;
		int screenWidth = ScreenUtils.getDisplayWidth(getContext());
		int screenHeight = ScreenUtils.getDisplayHeight(getContext());
		float screenProportion = (float) screenWidth / (float) screenHeight;

		ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
		if (videoProportion > screenProportion) {
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = (int) ((float) screenWidth / videoProportion);
		} else {
			layoutParams.width = (int) (videoProportion * (float) screenHeight);
			layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
		}
		surfaceView.setLayoutParams(layoutParams);
	}
}
