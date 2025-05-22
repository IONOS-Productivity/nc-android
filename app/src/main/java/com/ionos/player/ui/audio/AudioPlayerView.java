package com.ionos.player.ui.audio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.ionos.player.ui.message.PlayerMessageBuilderFactory;
import com.ionos.player.ui.sources.PlayerSourcesView;
import com.ionos.player.util.Cast;
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
public class AudioPlayerView extends PlayerView {

	@Inject
    PlaybackModel playerModel;
	@Inject
	PlayerMessageBuilderFactory messageBuilderFactory;
	@Inject
	FileBeingProcessedPredicate fileBeingProcessedPredicate;

	private final TextView tvTitle;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private final DrawerLayout drawerLayout;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter hidingPresenter;

	public AudioPlayerView(Context context) {
		super(context);
		inflate(context, R.layout.player_audio_view, this);

		this.playerSourcesView = findViewById(R.id.playerSourcesView);
		this.playerControlView = findViewById(R.id.playerControlView);
		this.drawerLayout = findViewById(R.id.drawer_layout);
		this.tvTitle = findViewById(R.id.tvTitle);

		ImageView ivBack = findViewById(R.id.ivBack);

		if (isInEditMode()) {
			return;
		}

		inject(context);

		this.playerViewContainer = Cast.castOrError(context, PlayerViewContainer.class);

		ivBack.setOnClickListener(view -> {
			playerViewContainer.onPlayerViewClose();
		});

		this.hidingPresenter = new MultiplePlayerHidingPresenter(this.playerModel);

		this.playerSourcesView.init(new AudioPlayerSourceFragmentFactory());

		Cast.castOrError(context, Activity.class).setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

	protected void inject(@NonNull Context context) {
        ((HasAndroidInjector) context.getApplicationContext()).androidInjector().inject(this);
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
	}

	@CallSuper
	@Override
	public void onStop() {
		this.playerSourcesView.onStop();
		this.playerControlView.onStop();
		this.hidingPresenter.onDisappear();
		this.playerModel.removeListener(this.playerModelListener);
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
			if (currentFileListener != null) {
				currentFileListener.fileChanged(file);
			}
			showMessageThatSelectedFilesAreBeingProcessing(file);
			this.tvTitle.setText(file.getName());
		} else {
			this.tvTitle.setText("");
		}
	}

	private void showMessageThatSelectedFilesAreBeingProcessing(PlaybackFile selectedFile) {
		boolean hasCurrentlyProcessingFiles = fileBeingProcessedPredicate.satisfied(selectedFile);

		if (hasCurrentlyProcessingFiles) {
			messageBuilderFactory.show(
					getContext(),
					getContext().getString(R.string.player_files_are_processing_alert_message)
			);
		}
	}

	private final PlaybackModel.Listener playerModelListener = new PlaybackModel.Listener() {
		@Override
		public void onUpdate(PlaybackState state) {
			updateState();
		}

		@Override
		public void onError(Throwable error) { /**/ }

		@Override
		public void onFilesChanged(List<PlaybackFile> originalFiles, List<PlaybackFile> currentFiles) { /**/ }
	};

	private final MultiplePlayer.HidingView hidingView = new MultiplePlayer.HidingView() {
		@Override
		public void displayPlayerView() { /**/ }

		@Override
		public void doNotDisplayPlayerView() {
			playerViewContainer.onPlayerViewClose();
		}
	};
}
