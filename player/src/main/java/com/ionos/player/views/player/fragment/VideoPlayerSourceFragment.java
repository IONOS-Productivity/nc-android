package com.ionos.player.views.player.fragment;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.ionos.player.R;
import com.ionos.player.chromecast.PlayerChromecastModel;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.image_loading.PlayerImageLoader;
import com.ionos.player.image_loading.PlayerImageLoaderOptions;
import com.ionos.player.player_mode.EmptyPlayerModeModel;
import com.ionos.player.player_mode.PlayerMode;
import com.ionos.player.player_mode.PlayerModeModel;
import com.ionos.player.player_mode.PlayerModePresenter;
import com.ionos.player.predicate.IsVideoPredicate;
import com.ionos.player.util.ScreenUtils;
import com.ionos.player.util.SystemVersion;
import com.ionos.player.views.player.presenter.ChromecastVideoErrorPresenter;
import com.ionos.player.views.player.view.ChromecastErrorView;
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
	java.util.Optional<PlayerChromecastModel> chromecastModel;
	@Inject
	PlayerImageLoader imageLoader;
	@Inject
	IsVideoPredicate isVideoPredicate;

	private PlayerFileInfo fileInfo;
	private MultiplePlayer.VideoPresenter<PlayerFileInfo> videoPresenter;
	private PlayerMode.Presenter playerModePresenter;
	private PlayerMode.Model playerModeModel;

	private View coverContainer;
	private View videoContainer;
	private View chromecastCoverContainer;
	private ImageView chromecastCover;
	private TextView chromecastTitleView;
	private SurfaceView surfaceView;
	private ProgressBar progressBar;
	private SurfaceVideoView<PlayerFileInfo> surfaceVideoView;
	private Optional<VideoSize> previousVideoSize = Optional.empty();
	private ChromecastVideoErrorPresenter chromecastVideoErrorPresenter;

	final ChromecastErrorView chromecastVideoErrorView = new ChromecastErrorView() {
		@Override
		public void showErrorPlaceholder() {
			if (isCastingToChromecast()) {
				if (isAdded()) {
					progressBar.setVisibility(View.GONE);
					chromecastCover.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_player_movie_white_48dp));
					chromecastCover.setVisibility(View.VISIBLE);
				}
			}
		}
	};

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
		if (chromecastModel.isPresent()) {
			this.playerModeModel = new PlayerModeModel(this.chromecastModel.get());
		}else {
			this.playerModeModel = new EmptyPlayerModeModel();
		}
		this.playerModePresenter = new PlayerModePresenter(this.playerModeModel);
		this.chromecastVideoErrorPresenter = new ChromecastVideoErrorPresenter(this.chromecastVideoErrorView, this.playerModel);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_video_player_source, container, false);
		this.coverContainer = content.findViewById(R.id.album_cover_container);
		this.videoContainer = content.findViewById(R.id.videoContainer);
		this.chromecastCoverContainer = content.findViewById(R.id.chromecastCoverContainer);
		this.chromecastCover = content.findViewById(R.id.chromecast_cover);
		this.chromecastTitleView = content.findViewById(R.id.chromecastTitle);
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
		this.playerModePresenter.setView(this.playerModeView);
		this.playerModePresenter.onCreate();
		this.chromecastVideoErrorPresenter.onStart();
	}

	@Override
	public void onDestroyView() {
		this.videoPresenter.setView(null);
		this.videoPresenter.onDestroy();
		this.surfaceVideoView.onDestroy();
		this.playerModePresenter.setView(null);
		this.playerModePresenter.onDestroy();
		this.chromecastVideoErrorPresenter.onStop();
		super.onDestroyView();
	}

	@Override
	public void onStart() {
		super.onStart();
		this.videoPresenter.onAppear();
		this.surfaceVideoView.onAppear();
		this.playerModePresenter.onAppear();

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
		this.playerModePresenter.onDisappear();

		SurfaceInvalidator surfaceInvalidator = getSurfaceInvalidator();
		if (surfaceInvalidator != null && surfaceView != null) {
			surfaceInvalidator.removeSurface(surfaceView);
		}

		super.onStop();
	}

	private void switchToCoverContainer() {
		setContainersVisibility(true, false, false);
	}

	private void switchToVideoContainer() {
		setContainersVisibility(false, true, false);
	}

	private void switchToChromecastCoverContainer() {
		setContainersVisibility(false, false, true);
		String deviceName = chromecastModel.get().getCastDeviceName();
		if (!deviceName.isEmpty()) {
			chromecastTitleView.setVisibility(View.VISIBLE);
			chromecastTitleView.setText(getString(R.string.player_casting_to_text) + " " + deviceName);
		}
		progressBar.setVisibility(View.VISIBLE);
        var requestBuilder = imageLoader.load(fileInfo);
        if (requestBuilder != null) {
            requestBuilder
				.onSuccess(() -> progressBar.setVisibility(View.GONE))
				.onError(() -> progressBar.setVisibility(View.GONE))
				.errorResources(R.drawable.ic_player_movie_white_48dp)
				.options(new PlayerImageLoaderOptions(PlayerImageLoaderOptions.ScaleType.CENTER_INSIDE))
				.into(chromecastCover);
            }
	}

	private void setContainersVisibility(boolean songContainerVisible, boolean videoContainerVisible, boolean chromecastCoverVisible) {
		this.coverContainer.setVisibility(songContainerVisible ? View.VISIBLE : View.INVISIBLE);
		this.videoContainer.setVisibility(videoContainerVisible ? View.VISIBLE : View.INVISIBLE);
		this.surfaceView.setVisibility(videoContainerVisible ? View.VISIBLE : View.INVISIBLE);
		this.chromecastCoverContainer.setVisibility(chromecastCoverVisible ? View.VISIBLE : View.INVISIBLE);
	}

	private final PlayerMode.View playerModeView = new PlayerMode.View() {
		@Override
		public void switchToRegularMode() {
			switchToCoverContainer();
		}

		@Override
		public void switchToChromecastMode() {
			if (chromecastModel.isPresent()) {
				switchToChromecastCoverContainer();
			}
		}
	};

	private final MultiplePlayer.VideoView<PlayerFileInfo> videoView = new MultiplePlayer.VideoView<>() {
		@Override
		public void setVideoViewAvailable() {
			if (isCastingToChromecast()) {
				switchToChromecastCoverContainer();
			} else {
				switchToVideoContainer();
				final Optional<VideoSize> videoSize = playerModel.getState().get().getCurrentPlaybackState().get().videoSize;
				if (videoSize.isPresent()) {
					if (!previousVideoSize.equals(videoSize)) {
						previousVideoSize = videoSize;
						setVideoSize(videoSize.get().getWidth(), videoSize.get().getHeight());
					}
				}
			}
		}

		@Override
		public void setVideoViewUnavailable() {
			switchToCoverContainer();
			chromecastCover.setImageResource(android.R.color.transparent);
		}

		@Override
		public void setVideoView(VideoViewSetter videoViewSetter, PlayerFileInfo fileInfo) {
		}

		@Override
		public void clearVideoView(VideoViewSetter setter) {
		}
	};

	private boolean isCastingToChromecast() {
		return playerModeModel.getMode() == PlayerMode.Mode.CHROMECAST;
	}

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
