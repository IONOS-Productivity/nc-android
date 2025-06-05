package com.ionos.player.ui.video;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.ionos.player.model.PlaybackFile;
import com.ionos.player.model.PlaybackModel;
import com.ionos.player.model.predicate.FileBeingProcessedPredicate;
import com.ionos.player.model.state.PlaybackItemState;
import com.ionos.player.model.state.PlaybackState;
import com.ionos.player.ui.MultiplePlayer;
import com.ionos.player.ui.MultiplePlayerHidingPresenter;
import com.ionos.player.ui.PlayerView;
import com.ionos.player.ui.PlayerViewContainer;
import com.ionos.player.ui.control.PlayerControlView;
import com.ionos.player.ui.sources.PlayerSourcesView;
import com.ionos.player.util.Action;
import com.ionos.player.util.AsyncTimer;
import com.ionos.player.util.Cast;
import com.ionos.player.util.TimeSkippableActionExecutor;
import com.ionos.player.util.WindowWrapper;
import com.owncloud.android.R;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import dagger.android.HasAndroidInjector;

/**
 * Created by yaz on 9/20/16.
 */
public class VideoPlayerView extends PlayerView {
	protected static final long ANIMATION_TIMER_DURATION = 5000;

	@Inject
	PlaybackModel playerModel;
	@Inject
	FileBeingProcessedPredicate fileBeingProcessedPredicate;

	private final TextView tvTitle;
	private final LinearLayout topBar;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private final DrawerLayout drawerLayout;
	private Activity activity;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter hidingPresenter;
	private AsyncTimer timer;
	private final TimeSkippableActionExecutor timeSkipableActionExecutor = new TimeSkippableActionExecutor();

	public VideoPlayerView(Context context) {
		super(context);
		inflate(context, R.layout.player_video_view, this);

		this.playerSourcesView = findViewById(R.id.playerSourcesView);
		this.playerControlView = findViewById(R.id.playerControlView);
		this.drawerLayout = findViewById(R.id.drawer_layout);
		this.tvTitle = findViewById(R.id.tvTitle);
		this.topBar = findViewById(R.id.topBar);

		ImageView ivBack = findViewById(R.id.ivBack);

		if (isInEditMode()) {
			return;
		}

		inject(context);

		this.activity = Cast.castOrError(context, Activity.class);
		this.playerViewContainer = Cast.castOrError(context, PlayerViewContainer.class);

		ivBack.setOnClickListener(this.backButtonClickListener);

		this.drawerLayout.addDrawerListener(this.drawerListener);

		this.hidingPresenter = new MultiplePlayerHidingPresenter(this.playerModel);

		this.playerSourcesView.init(new VideoPlayerSourceFragmentFactory());

        this.activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		this.timer = new AsyncTimer(ANIMATION_TIMER_DURATION);
		this.timer.start();
	}

	protected void inject(@NonNull Context context){
        ((HasAndroidInjector) context.getApplicationContext()).androidInjector().inject(this);
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		timeSkipableActionExecutor.execute(new Action() {
			@Override
			public void execute() {
				if (playerControlView.getVisibility() == INVISIBLE) {
					showControls();
					startTimer();
				} else {
					final boolean touchOutsideControls = ev.getY() < playerControlView.getY() && ev.getY() > topBar.getHeight();
					if (touchOutsideControls) {
						hideControls();
					} else {
						startTimer();
					}
				}
			}
		}, 500);
		return super.dispatchTouchEvent(ev);
	}

	private void startTimer() {
		timer.stop();
		timer.start();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (isInEditMode()) {
			return;
		}
		this.hidingPresenter.setView(this.hidingView);
		this.hidingPresenter.onCreate();
	}

	@Override
	protected void onDetachedFromWindow() {
		if (isInEditMode()) {
			return;
		}
		this.hidingPresenter.setView(null);
		this.hidingPresenter.onDestroy();
		super.onDetachedFromWindow();
	}

	@CallSuper
	@Override
	public void onStart() {
		this.playerSourcesView.onStart();
		this.playerControlView.onStart();
		this.hidingPresenter.onAppear();
		this.playerModel.addListener(this.playerModelListener);
		this.timer.subscribe(this.timerCallbackListener);
	}

	@CallSuper
	@Override
	public void onStop() {
		this.playerSourcesView.onStop();
		this.playerControlView.onStop();
		this.hidingPresenter.onDisappear();
		this.playerModel.removeListener(this.playerModelListener);
		this.timer.unSubscribe();
	}

	protected void updateState() {
		if (!this.playerModel.getState().isPresent()) {
			playerViewContainer.onPlayerViewClose();
			return;
		}
		PlaybackState state = this.playerModel.getState().get();

		if (state.currentItemState.isPresent()) {
			PlaybackItemState playbackItemState = state.currentItemState.get();
			PlaybackFile file = playbackItemState.file;
			if (currentFileListener != null){
				currentFileListener.fileChanged(file);
			}
			showMessageThatSelectedFilesAreBeingProcessing(file);
			this.tvTitle.setText(file.getNameWithoutExtension());
		} else {
            this.tvTitle.setText("");
		}
	}

	private void showMessageThatSelectedFilesAreBeingProcessing(PlaybackFile selectedFile) {
		boolean hasCurrentlyProcessingFiles = fileBeingProcessedPredicate.satisfied(selectedFile);

		if (hasCurrentlyProcessingFiles) {
			Snackbar.make(this, R.string.player_files_are_processing_alert_message, Snackbar.LENGTH_LONG).show();
		}
	}

	private final OnClickListener backButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			playerViewContainer.onPlayerViewClose();
		}
	};

	private final DrawerLayout.SimpleDrawerListener drawerListener =  new DrawerLayout.SimpleDrawerListener() {
		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			if (slideOffset != 0) {
				onDrawerOpened(drawerView);
			} else {
				onDrawerClosed(drawerView);
			}
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			WindowWrapper windowWrapper = new WindowWrapper(activity.getWindow());
			windowWrapper.clearFullScreenFlags();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			WindowWrapper windowWrapper = new WindowWrapper(activity.getWindow());
			windowWrapper.setFullScreenFlags();
		}
	};

	private final PlaybackModel.Listener playerModelListener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {
			updateState();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) {

		}
	};

	private final MultiplePlayer.HidingView hidingView = new MultiplePlayer.HidingView() {
		@Override
		public void displayPlayerView() {

		}

		@Override
		public void doNotDisplayPlayerView() {
			playerViewContainer.onPlayerViewClose();
		}
	};

	private final AsyncTimer.TimerCallbackListener timerCallbackListener = this::hideControls;

	public void showControls() {
		playerControlView.setVisibility(VISIBLE);
		topBar.setVisibility(VISIBLE);
	}

	public void hideControls() {
		topBar.setVisibility(GONE);
		playerControlView.setVisibility(INVISIBLE);
	}
}

