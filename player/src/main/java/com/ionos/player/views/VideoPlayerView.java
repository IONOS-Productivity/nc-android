package com.ionos.player.views;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ionos.player.R;
import com.ionos.player.chromecast.PlayerChromecastModel;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.message.PlayerMessageBuilderFactory;
import com.ionos.player.predicate.FileBeingProcessedPredicate;
import com.ionos.player.transformation.FileInfoToDisplayNameTransformation;
import com.ionos.player.util.Action;
import com.ionos.player.util.AsyncTimer;
import com.ionos.player.util.Cast;
import com.ionos.player.util.TimeSkippableActionExecutor;
import com.ionos.player.util.WindowWrapper;
import com.ionos.player.views.player.fragment.VideoPlayerSourceFragmentFactory;
import com.ionos.player.views.player.view.PlayerControlView;
import com.ionos.player.views.player.view.PlayerSourcesView;
import com.ionos.player.views.player.view.PlayerView;
import com.ionos.player.views.player.view.PlayerViewContainer;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;
import com.ionos.player.multipleplayermvp.presenter.MultiplePlayerHidingPresenter;
import com.ionos.player.player.interfaces.PlaybackState;
import com.ionos.player.player.volume.Volume;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Created by yaz on 9/20/16.
 */
public class VideoPlayerView extends PlayerView {
	protected static final long ANIMATION_TIMER_DURATION = 5000;

	@Inject
	MultiplePlayer.Model<PlayerFileInfo> playerModel;
	@Inject
	Optional<PlayerChromecastModel> chromecastModel;
	@Inject
	PlayerMessageBuilderFactory messageBuilderFactory;
	@Inject
	FileBeingProcessedPredicate fileBeingProcessedPredicate;
	@Inject
	FileInfoToDisplayNameTransformation fileInfoToDisplayNameTransformation;

	private final TextView tvTitle;
	private final LinearLayout topBar;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private final DrawerLayout drawerLayout;
	private Activity activity;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter<PlayerFileInfo> hidingPresenter;
	private AsyncTimer timer;
	private final TimeSkippableActionExecutor timeSkipableActionExecutor = new TimeSkippableActionExecutor();

	public VideoPlayerView(Context context) {
		super(context);
		inflate(context, R.layout.view_video_player, this);

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

		this.hidingPresenter = new MultiplePlayerHidingPresenter<>(this.playerModel);

		this.playerSourcesView.init(new VideoPlayerSourceFragmentFactory());

		new Volume().useVolumeKeysToControlPlaybackVolume(activity);

		this.timer = new AsyncTimer(ANIMATION_TIMER_DURATION);
		this.timer.start();
	}

	protected void inject(@NonNull Context context){
		PlayerComponent.Companion.from(context).inject(this);
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
		if(chromecastModel.isPresent()) {
			this.chromecastModel.get().addReceiverStateChangeListener(this.receiverStateChangeListener);
		}
	}

	@CallSuper
	@Override
	public void onStop() {
		this.playerSourcesView.onStop();
		this.playerControlView.onStop();
		this.hidingPresenter.onDisappear();
		this.playerModel.removeListener(this.playerModelListener);
		this.timer.unSubscribe();
		if(chromecastModel.isPresent()) {
			this.chromecastModel.get().removeReceiverStateChangeListener(this.receiverStateChangeListener);
		}
	}

	protected void updateState() {
		if (!this.playerModel.getState().isPresent()) {
			playerViewContainer.onPlayerViewClose();
			return;
		}
		MultiplePlaybackState<PlayerFileInfo> state = this.playerModel.getState().get();

		if (state.getCurrentPlaybackState().isPresent()) {
			PlaybackState<PlayerFileInfo> playbackState = state.getCurrentPlaybackState().get();
			PlayerFileInfo file = playbackState.sourceInfo;
			if (currentFileListener != null){
				currentFileListener.fileChanged(file);
			}
			showMessageThatSelectedFilesAreBeingProcessing(file);
			this.tvTitle.setText(fileInfoToDisplayNameTransformation.transform(file));
		} else {
            this.tvTitle.setText("");
		}
	}

	private void showMessageThatSelectedFilesAreBeingProcessing(PlayerFileInfo selectedFile) {
		boolean hasCurrentlyProcessingFiles = fileBeingProcessedPredicate.satisfied(selectedFile);

		if (hasCurrentlyProcessingFiles) {
			messageBuilderFactory.
					show(
							getContext(),
							getContext().getString(R.string.player_files_are_processing_alert_message)
					);
		}
	}

	private final OnClickListener backButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			playerViewContainer.onPlayerViewClose();
		}
	};

	private final PlayerChromecastModel.ReceiverStateChangeListener receiverStateChangeListener = new PlayerChromecastModel.ReceiverStateChangeListener() {

		@Override
		public void onStateChange() {
			updateState();
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

	private final MultiplePlayer.Model.Listener<PlayerFileInfo> playerModelListener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState<PlayerFileInfo> state) {
			updateState();
		}

		@Override
		public void onError(Throwable error) {
		}

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> list, List<PlayerFileInfo> list1) {

		}
	};

	private final MultiplePlayer.HidingView<PlayerFileInfo> hidingView = new MultiplePlayer.HidingView<>() {
		@Override
		public void displayPlayerView() {

		}

		@Override
		public void doNotDisplayPlayerView() {
			playerViewContainer.onPlayerViewClose();
		}
	};

	private final AsyncTimer.TimerCallbackListener timerCallbackListener = this::hideControls;

	private void showControls() {
		playerControlView.setVisibility(VISIBLE);
		topBar.setVisibility(VISIBLE);
	}

	private void hideControls() {
		topBar.setVisibility(GONE);
		playerControlView.setVisibility(INVISIBLE);
	}
}

