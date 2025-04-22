package com.ionos.player.player_mode;

import static com.ionos.player.player_mode.PlayerMode.Mode.CHROMECAST;
import static com.ionos.player.player_mode.PlayerMode.Mode.REGULAR;

/**
 * Created by: Alex Kucherenko
 * Date: 23.02.2017.
 */

public class PlayerModePresenter implements PlayerMode.Presenter {

	private final PlayerMode.Model model;
	private PlayerMode.View view = new NullPlayerModeView();

	public PlayerModePresenter(PlayerMode.Model model) {
		this.model = model;
	}

	@Override
	public void onCreate() {
		this.model.setListener(this.modelListener);
		this.model.init();
	}

	@Override
	public void onDestroy() {
		this.model.setListener(null);
		this.model.release();
	}

	@Override
	public void onAppear() {

	}

	@Override
	public void onDisappear() {

	}

	@Override
	public void setView(PlayerMode.View view) {
		this.view = view == null ? new NullPlayerModeView() : view;
	}

	private final PlayerMode.Model.Listener modelListener = new PlayerMode.Model.Listener() {
		@Override
		public void onModeChanged(PlayerMode.Mode mode) {
			if (mode == REGULAR) {
				view.switchToRegularMode();
			} else if (mode == CHROMECAST) {
				view.switchToChromecastMode();
			}
		}
	};
}
