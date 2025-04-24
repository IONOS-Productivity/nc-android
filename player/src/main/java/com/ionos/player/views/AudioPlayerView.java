package com.ionos.player.views;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ionos.player.R;
import com.ionos.player.di.PlayerComponent;
import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.message.PlayerMessageBuilderFactory;
import com.ionos.player.predicate.FileBeingProcessedPredicate;
import com.ionos.player.transformation.FileInfoToDisplayNameTransformation;
import com.ionos.player.util.Cast;
import com.ionos.player.views.player.fragment.AudioPlayerSourceFragmentFactory;
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

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Created by yaz on 9/20/16.
 */
public class AudioPlayerView extends PlayerView {

	@Inject
	MultiplePlayer.Model<PlayerFileInfo> playerModel;
	@Inject
	PlayerMessageBuilderFactory messageBuilderFactory;
	@Inject
	FileBeingProcessedPredicate fileBeingProcessedPredicate;
	@Inject
	FileInfoToDisplayNameTransformation fileInfoToDisplayNameTransformation;

	private final TextView tvTitle;
	private final PlayerSourcesView playerSourcesView;
	private final PlayerControlView playerControlView;
	private final DrawerLayout drawerLayout;
	private PlayerViewContainer playerViewContainer;
	private MultiplePlayer.HidingPresenter<PlayerFileInfo> hidingPresenter;

	public AudioPlayerView(Context context) {
		super(context);
		inflate(context, R.layout.view_audio_player, this);

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

		this.hidingPresenter = new MultiplePlayerHidingPresenter<>(this.playerModel);

		this.playerSourcesView.init(new AudioPlayerSourceFragmentFactory());

		new Volume().useVolumeKeysToControlPlaybackVolume(Cast.castOrError(context, Activity.class));
	}

	protected void inject(@NonNull Context context) {
		PlayerComponent.Companion.from(context).inject(this);
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
		MultiplePlaybackState<PlayerFileInfo> state = this.playerModel.getState().get();
		if (state.getCurrentPlaybackState().isPresent()) {
			PlaybackState<PlayerFileInfo> playbackState = state.getCurrentPlaybackState().get();
			PlayerFileInfo file = playbackState.sourceInfo;
			if (currentFileListener != null) {
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
			messageBuilderFactory.show(
					getContext(),
					getContext().getString(R.string.player_files_are_processing_alert_message)
			);
		}
	}

	private final MultiplePlayer.Model.Listener<PlayerFileInfo> playerModelListener = new MultiplePlayer.Model.Listener<>() {
		@Override
		public void onUpdate(MultiplePlaybackState<PlayerFileInfo> state) {
			updateState();
		}

		@Override
		public void onError(Throwable error) { /**/ }

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> list, List<PlayerFileInfo> list1) { /**/ }
	};

	private final MultiplePlayer.HidingView<PlayerFileInfo> hidingView = new MultiplePlayer.HidingView<PlayerFileInfo>() {
		@Override
		public void displayPlayerView() { /**/ }

		@Override
		public void doNotDisplayPlayerView() {
			playerViewContainer.onPlayerViewClose();
		}
	};
}
