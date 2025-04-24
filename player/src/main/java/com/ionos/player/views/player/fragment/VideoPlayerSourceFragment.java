package com.ionos.player.views.player.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.annimon.stream.Optional;
import com.ionos.player.R;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.image_loading.PlayerImageLoader;
import com.ionos.player.predicate.IsVideoPredicate;
import com.ionos.player.util.ScreenUtils;
import com.ionos.player.util.SystemVersion;
import com.ionos.player.views.player.view.FileTextDetailView;
import com.ionos.player.views.player.view.PlayerCompatible;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.null_object.NullMultiplePlayerVideoPresenter;
import com.ionos.player.multipleplayermvp.presenter.MultiplePlayerVideoPresenter;
import com.ionos.player.player.interfaces.VideoSize;
import com.ionos.player.player.interfaces.VideoViewSetter;
import com.ionos.player.player.video.SurfaceVideoView;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Sergey Shandyuk on 4/15/2016.
 */

public class VideoPlayerSourceFragment extends Fragment {

	private final static String ARGUMENT_FILE_INFO = "ARGUMENT_FILE_INFO";

	@Inject
	MultiplePlayer.Model<PlayerFileInfo> playerModel;
	@Inject
	PlayerImageLoader imageLoader;
	@Inject
	IsVideoPredicate isVideoPredicate;

	private PlayerFileInfo fileInfo;
	private MultiplePlayer.VideoPresenter<PlayerFileInfo> videoPresenter;

	private View coverContainer;
	private View videoContainer;
	private SurfaceView surfaceView;
	private ProgressBar progressBar;
	private SurfaceVideoView<PlayerFileInfo> surfaceVideoView;
	private Optional<VideoSize> previousVideoSize = Optional.empty();

	public static Fragment createInstance(PlayerFileInfo fileInfo) {
		VideoPlayerSourceFragment fragment = new VideoPlayerSourceFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARGUMENT_FILE_INFO, fileInfo);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PlayerComponent.Companion.from(requireContext()).inject(this);
		this.fileInfo = (PlayerFileInfo) getArguments().getSerializable(ARGUMENT_FILE_INFO);
		this.videoPresenter = isVideoPredicate.satisfied(fileInfo)
				? new MultiplePlayerVideoPresenter<>(this.playerModel, this.fileInfo)
				: NullMultiplePlayerVideoPresenter.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_video_player_source, container, false);
		this.coverContainer = content.findViewById(R.id.album_cover_container);
		this.videoContainer = content.findViewById(R.id.videoContainer);
		this.surfaceView = content.findViewById(R.id.surfaceView);
		this.progressBar = content.findViewById(R.id.progressBar);
		FileTextDetailView fileTextDetailView = content.findViewById(R.id.fileDetailView);
		MultiplePlayerVideoPresenter<PlayerFileInfo> surfaceVideoViewPresenter = new MultiplePlayerVideoPresenter<>(this.playerModel, this.fileInfo);
		this.surfaceVideoView = new SurfaceVideoView<>(this.surfaceView, surfaceVideoViewPresenter);
		surfaceVideoViewPresenter.setView(this.surfaceVideoView);
		fileTextDetailView.displayFileInfo(this.fileInfo);
		switchToCoverContainer();
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

	private void switchToCoverContainer() {
		setContainersVisibility(true, false);
	}

	private void switchToVideoContainer() {
		setContainersVisibility(false, true);
	}

	private void setContainersVisibility(boolean songContainerVisible, boolean videoContainerVisible) {
		this.coverContainer.setVisibility(songContainerVisible ? View.VISIBLE : View.INVISIBLE);
		this.videoContainer.setVisibility(videoContainerVisible ? View.VISIBLE : View.INVISIBLE);
		this.surfaceView.setVisibility(videoContainerVisible ? View.VISIBLE : View.INVISIBLE);
	}

	private final MultiplePlayer.VideoView<PlayerFileInfo> videoView = new MultiplePlayer.VideoView<>() {
		@Override
		public void setVideoViewAvailable() {
            switchToVideoContainer();
            final Optional<VideoSize> videoSize = playerModel.getState().get().getCurrentPlaybackState().get().videoSize;
            if (videoSize.isPresent()) {
                if (!previousVideoSize.equals(videoSize)) {
                    previousVideoSize = videoSize;
                    setVideoSize(videoSize.get().getWidth(), videoSize.get().getHeight());
                }
            }
		}

		@Override
		public void setVideoViewUnavailable() {
			switchToCoverContainer();
		}

		@Override
		public void setVideoView(VideoViewSetter videoViewSetter, PlayerFileInfo fileInfo) {
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
