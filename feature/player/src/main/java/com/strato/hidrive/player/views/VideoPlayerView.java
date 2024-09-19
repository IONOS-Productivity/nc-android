package com.strato.hidrive.player.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.strato.hidrive.player.R;
import com.strato.hidrive.player.chromecast.PlayerChromecastModel;
import com.strato.hidrive.player.di.PlayerComponent;
import com.strato.hidrive.player.di.PlayerExifInfoViewDependencies;
import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.message.PlayerMessageBuilderFactory;
import com.strato.hidrive.player.player_mode.PlayerMode;
import com.strato.hidrive.player.predicate.FileBeingProcessedPredicate;
import com.strato.hidrive.player.tracking.VideoPlayerEventTracker;
import com.strato.hidrive.player.tracking.VideoPlayerEventTrackerPlayerControlViewListener;
import com.strato.hidrive.player.transformation.FileInfoToDisplayNameTransformation;
import com.strato.hidrive.player.transformation.PlayerFileInfoToExifInfoProviderTransformation;
import com.strato.hidrive.player.util.Action;
import com.strato.hidrive.player.util.AsyncTimer;
import com.strato.hidrive.player.util.Cast;
import com.strato.hidrive.player.util.PlayerSwipeDirection;
import com.strato.hidrive.player.util.TimeSkippableActionExecutor;
import com.strato.hidrive.player.util.WindowWrapper;
import com.strato.hidrive.player.views.player.fragment.VideoPlayerSourceFragmentFactory;
import com.strato.hidrive.player.views.player.presenter.ExifInfoPlayerPresenter;
import com.strato.hidrive.player.views.player.view.PlayerControlView;
import com.strato.hidrive.player.views.player.view.PlayerSourcesView;
import com.strato.hidrive.player.views.player.view.PlayerView;
import com.strato.hidrive.player.views.player.view.PlayerViewContainer;
import com.strato.hidrive.stylized_view.StylizedTextView;
import com.strato.hidrive.views.contextbar.ContextActionBar;
import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy;
import com.strato.hidrive.views.contextbar.strategy.configuration.ToolbarItemClickListener;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.exif_info.ExifInfoView;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;
import com.viseven.develop.multipleplayermvp.presenter.MultiplePlayerHidingPresenter;
import com.viseven.develop.player.interfaces.PlaybackState;
import com.viseven.develop.player.volume.Volume;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaz on 9/20/16.
 */
public class VideoPlayerView extends PlayerView {
	protected static final long ANIMATION_TIMER_DURATION = 5000;

	@Inject
	MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> playerModel;
	@Inject
	Optional<PlayerChromecastModel> chromecastModel;
	@Inject
	PlayerMessageBuilderFactory messageBuilderFactory;
	@Inject
	PlayerCABStrategyProvider playerCABStrategyProvider;
	@Inject
	FileBeingProcessedPredicate fileBeingProcessedPredicate;
	@Inject
	PlayerFileInfoToExifInfoProviderTransformation remoteFileInfoToExifInfoProviderTransformation;
	@Inject
	FileInfoToDisplayNameTransformation fileInfoToDisplayNameTransformation;
	@Inject
	PlayerExifInfoViewDependencies exifInfoViewDependencies;

	private final StylizedTextView tvTitle;
	private final RelativeLayout topBar;
	private final ContextActionBar contextActionBar;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private final DrawerLayout drawerLayout;
	private final ExifInfoView exifInfoView;
	private Activity activity;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter<PlayerFileInfo> hidingPresenter;
	protected ExifInfoPlayerPresenter exifInfoPlayerPresenter;
	private AsyncTimer timer;
	private final TimeSkippableActionExecutor timeSkipableActionExecutor = new TimeSkippableActionExecutor();
	private final CompositeDisposable compositeDisposable = new CompositeDisposable();
	private VideoPlayerEventTrackerPlayerControlViewListener videoPlayerEventTrackerPlayerControlViewListener;
	protected VideoPlayerEventTracker eventTracker;

	public VideoPlayerView(Context context, VideoPlayerEventTracker eventTracker) {
		super(context);
		inflate(context, R.layout.view_video_player, this);

		this.contextActionBar = findViewById(R.id.topContextActionBar);
		this.playerSourcesView = findViewById(R.id.playerSourcesView);
		this.playerControlView = findViewById(R.id.playerControlView);
		this.drawerLayout = findViewById(R.id.drawer_layout);
		this.tvTitle = findViewById(R.id.tvTitle);
		this.topBar = findViewById(R.id.topBar);
		this.exifInfoView = findViewById(R.id.exif_info_view);

		ImageView ivBack = findViewById(R.id.ivBack);

		if (isInEditMode()) {
			return;
		}

		inject(context);

		this.eventTracker = eventTracker;

		this.activity = Cast.castOrError(context, Activity.class);
		this.playerViewContainer = Cast.castOrError(context, PlayerViewContainer.class);

		ivBack.setOnClickListener(this.backButtonClickListener);

		this.contextActionBar.setMoreMenuPosition(ContextActionBar.MoreMenuPosition.TOP_POSITION);
		this.contextActionBar.setCompactStyle(new AudioPlayerToolbarItemStyle());
		this.contextActionBar.setToolbarItemClickListener(this.toolbarItemClickListener);

		initExifInfo();

		this.drawerLayout.addDrawerListener(this.drawerListener);

		this.hidingPresenter = new MultiplePlayerHidingPresenter<>(this.playerModel);
		this.exifInfoPlayerPresenter = new ExifInfoPlayerPresenter(
				exifInfoView,
				this.playerModel,
				remoteFileInfoToExifInfoProviderTransformation
		);

		this.playerSourcesView.init(new VideoPlayerSourceFragmentFactory());

		new Volume().useVolumeKeysToControlPlaybackVolume(activity);

		this.timer = new AsyncTimer(ANIMATION_TIMER_DURATION);
		this.timer.start();

		this.videoPlayerEventTrackerPlayerControlViewListener =
				new VideoPlayerEventTrackerPlayerControlViewListener(this.eventTracker);
	}

	protected void inject(@NonNull Context context){
		PlayerComponent.Companion.from(context).inject(this);
	}

	private void initExifInfo(){
		this.exifInfoView.setExifInfoListener(() -> drawerLayout.closeDrawer(GravityCompat.END));
		this.exifInfoViewDependencies.inject(this.exifInfoView);
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
					final boolean touchOutsideControls = ev.getY() < playerControlView.getY() && ev.getY() > contextActionBar.getHeight();
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
		this.playerControlView.addListener(this.videoPlayerEventTrackerPlayerControlViewListener);
		this.playerSourcesView.addViewPagerOnPageChangeListener(this.onPageChangeListener);
	}

	@Override
	protected void onDetachedFromWindow() {
		if (isInEditMode()) {
			return;
		}
		this.hidingPresenter.setView(null);
		this.hidingPresenter.onDestroy();
		this.playerControlView.removeListener(this.videoPlayerEventTrackerPlayerControlViewListener);
		this.playerSourcesView.removeViewPagerOnPageChangeListener(this.onPageChangeListener);
		super.onDetachedFromWindow();
	}

	@CallSuper
	@Override
	public void onStart() {
		this.playerSourcesView.onStart();
		this.playerControlView.onStart();
		this.hidingPresenter.onAppear();
		this.exifInfoPlayerPresenter.onStart();
		this.playerModel.addListener(this.playerModelListener);
		this.timer.subscribe(this.timerCallbackListener);
		if(chromecastModel.isPresent()) {
			this.chromecastModel.get().addReceiverStateChangeListener(this.receiverStateChangeListener);
		}
	}

	@CallSuper
	@Override
	public void onStop() {
		this.compositeDisposable.clear();
		this.playerSourcesView.onStop();
		this.playerControlView.onStop();
		this.hidingPresenter.onDisappear();
		this.exifInfoPlayerPresenter.onStop();
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
		MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> state = this.playerModel.getState().get();

		if (state.getCurrentPlaybackState().isPresent()) {
			PlaybackState<PlayerFileInfo> playbackState = state.getCurrentPlaybackState().get();
			PlayerFileInfo file = playbackState.sourceInfo;
			if (currentFileListener != null){
				currentFileListener.fileChanged(file);
			}
			showMessageThatSelectedFilesAreBeingProcessing(file);
			updateToolbarStrategy(file);
			this.tvTitle.setText(fileInfoToDisplayNameTransformation.transform(file));
		} else {
			setToolbarStrategy(playerCABStrategyProvider.emptyNavigationBarStrategy());
		}
	}

	private void updateToolbarStrategy(@NonNull PlayerFileInfo file) {
		compositeDisposable.add(
				playerCABStrategyProvider.mediaPlayerTopNavigationStrategy(file)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(
								this::setToolbarStrategy,
								t-> Log.w(getClass().getSimpleName(), "failed to update toolbar strategy", t)
						)
		);
	}

	protected final void setToolbarStrategy(@NonNull ICABConfigurationStrategy strategy) {
		this.contextActionBar.setToolbarStrategy(strategy);
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

	private final ToolbarItemClickListener toolbarItemClickListener = new ToolbarItemClickListener() {
		@Override
		public boolean onToolbarItemClick(ToolbarItem item) {
			eventTracker.onToolbarItemClick(item);
			switch (item.getType()) {
				case INFO:
					drawerLayout.openDrawer(GravityCompat.END);
					return true;
				default:
					return onCustomToolbarItemClicked(item);
			}
		}
	};

	protected boolean onCustomToolbarItemClicked(@NonNull ToolbarItem item){
		return false;
	}

	private final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		private Optional<Integer> previousViewPagerPosition = Optional.of(-1);

		@Override
		public void onPageSelected(int newScreenIndex) {
			trackSwipe(newScreenIndex);
			previousViewPagerPosition = Optional.of(newScreenIndex);
		}

		private void trackSwipe(int newPosition) {
			if (previousViewPagerPosition.isPresent()) {
				Optional<PlayerSwipeDirection> direction = getSwipeDirection(newPosition, previousViewPagerPosition.get());
				if (direction.isPresent()) {
					eventTracker.trackSwipe(direction.get());
				}
			}
		}

		private Optional<PlayerSwipeDirection> getSwipeDirection(Integer currentPosition,
																 Integer previousPosition) {
			Optional<PlayerSwipeDirection> swipeDirection = Optional.empty();

			if (currentPosition > previousPosition) {
				swipeDirection = Optional.of(PlayerSwipeDirection.NEXT);
			} else if (currentPosition < previousPosition) {
				swipeDirection = Optional.of(PlayerSwipeDirection.PREVIOUS);
			}
			return swipeDirection;
		}
	};

	private final OnClickListener backButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			eventTracker.trackBackClicked();
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

	private final MultiplePlayer.Model.Listener<PlayerFileInfo, PlayerMode.Mode> playerModelListener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState<PlayerFileInfo, PlayerMode.Mode> state) {
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

