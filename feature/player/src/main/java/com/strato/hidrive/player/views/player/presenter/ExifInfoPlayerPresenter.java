package com.strato.hidrive.player.views.player.presenter;

import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.strato.hidrive.player.player_mode.PlayerMode;
import com.strato.hidrive.player.transformation.PlayerFileInfoToExifInfoProviderTransformation;
import com.strato.hidrive.views.exif_info.ExifInfoView;
import com.viseven.develop.multipleplayer.interfaces.MultiplePlaybackState;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;
import com.viseven.develop.player.interfaces.PlaybackState;

import java.util.List;
import java.util.Optional;

/**
 * Created by yaz on 1/17/17.
 */

public class ExifInfoPlayerPresenter {

	private final ExifInfoView view;
	private final MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> playerModel;
	private final PlayerFileInfoToExifInfoProviderTransformation remoteFileInfoToExifInfoProviderTransformation;
	private Optional<PlaybackState<PlayerFileInfo>> lastSavedState = Optional.empty();

	public ExifInfoPlayerPresenter(
			ExifInfoView view,
			MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> playerModel,
			PlayerFileInfoToExifInfoProviderTransformation remoteFileInfoToExifInfoProviderTransformation
	) {
		this.view = view;
		this.playerModel = playerModel;
		this.remoteFileInfoToExifInfoProviderTransformation = remoteFileInfoToExifInfoProviderTransformation;
	}

	public void onStart() {
		this.playerModel.addListener(this.playerModelListener);
	}

	public void onStop() {
		this.playerModel.removeListener(this.playerModelListener);
	}

	public void onFavoriteStatusChangedUpdated() {
		updateState();
	}

	private void updateState() {
		boolean hasState = this.playerModel.getState().isPresent() && this.playerModel.getState().get().getCurrentPlaybackState().isPresent();
		if (hasState) {
			PlaybackState<PlayerFileInfo> playbackState = this.playerModel.getState().get().getCurrentPlaybackState().get();
			PlayerFileInfo file = playbackState.sourceInfo;

			boolean playbackChanged = !this.lastSavedState.isPresent() || !this.lastSavedState.get().sourceInfo.equals(playbackState.sourceInfo);
			if (playbackChanged && this.view.isAttachedToWindow()) {
				this.lastSavedState = Optional.of(playbackState);
                var provider = remoteFileInfoToExifInfoProviderTransformation.transform(file);
                if (provider != null) this.view.loadMetaInfo(provider);
			}
		} else {
			this.view.clearItems();
			this.lastSavedState = Optional.empty();
		}
	}

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
}
