package com.ionos.player.player_mode;

import com.ionos.player.chromecast.PlayerChromecastModel;
import com.ionos.player.chromecast.PlayerChromecastModelState;

import androidx.annotation.NonNull;

import static com.ionos.player.player_mode.PlayerMode.Mode.CHROMECAST;
import static com.ionos.player.player_mode.PlayerMode.Mode.REGULAR;

/**
 * Created by: Alex Kucherenko
 * Date: 22.02.2017.
 */

public class PlayerModeModel implements PlayerMode.Model {

	private Listener listener = new NullPlayerModeModelListener();
	private PlayerMode.Mode mode;

	private final PlayerChromecastModel chromecastModel;

	public PlayerModeModel(PlayerChromecastModel chromecastModel) {
		this.chromecastModel = chromecastModel;
	}

	@Override
	public void init() {
		updateMode(this.chromecastModel.state());
		this.chromecastModel.addListener(this.chromecastListener);
	}

	@Override
	public void release() {
		this.chromecastModel.removeListener(this.chromecastListener);
	}

	@Override
	public void setListener(Listener listener) {
		this.listener = listener == null ? new NullPlayerModeModelListener() : listener;
	}

	private void updateMode(PlayerChromecastModelState state) {
		PlayerMode.Mode newMode = getPlayerMode(state);
		if (this.mode != newMode) {
			this.mode = newMode;
			this.listener.onModeChanged(newMode);
		}
	}

	@NonNull
	private PlayerMode.Mode getPlayerMode(PlayerChromecastModelState state) {
		return state.getConnected() ?
				CHROMECAST :
				REGULAR;
	}

	@Override
	public PlayerMode.Mode getMode() {
		return chromecastModel.state().getConnected() ?
				CHROMECAST :
				REGULAR;
	}

	private final PlayerChromecastModel.Listener chromecastListener = new PlayerChromecastModel.Listener() {
		@Override
		public void onUpdate(PlayerChromecastModelState state) {
			updateMode(state);
		}

		@Override
		public void onApplicationConnectedToCastSession() {

		}
	};

}
