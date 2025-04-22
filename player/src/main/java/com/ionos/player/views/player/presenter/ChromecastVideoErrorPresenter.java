package com.ionos.player.views.player.presenter;

import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.error.ChromecastUnsupportedFileException;
import com.ionos.player.views.player.view.ChromecastErrorView;
import com.ionos.player.multipleplayer.interfaces.MultiplePlaybackState;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;

import java.util.List;

/**
 * Created by Anton Shevchuk on 07.03.2017.
 */

public class ChromecastVideoErrorPresenter {
	private final ChromecastErrorView view;
	private final MultiplePlayer.Model<PlayerFileInfo> playerModel;

	public ChromecastVideoErrorPresenter(ChromecastErrorView view, MultiplePlayer.Model<PlayerFileInfo> playerModel) {
		this.view = view;
		this.playerModel = playerModel;
	}

	public void onStart() {
		playerModel.addListener(this.listener);
	}

	public void onStop() {
		playerModel.removeListener(this.listener);
	}

	private final MultiplePlayer.Model.Listener<PlayerFileInfo> listener = new MultiplePlayer.Model.Listener<>() {

		@Override
		public void onUpdate(MultiplePlaybackState multiplePlaybackState) {

		}

		@Override
		public void onError(Throwable throwable) {
			if (isChromecastUnsupportedFileException(throwable)) {
				view.showErrorPlaceholder();
			}
		}

		@Override
		public void onSourceInfosChanged(List<PlayerFileInfo> list, List<PlayerFileInfo> list1) {

		}

		private boolean isChromecastUnsupportedFileException(Throwable throwable) {
			return throwable instanceof ChromecastUnsupportedFileException
					|| throwable.getCause() instanceof ChromecastUnsupportedFileException;
		}
	};
}
