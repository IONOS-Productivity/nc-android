package com.strato.hidrive.player.player_mode;

import static com.strato.hidrive.player.player_mode.PlayerMode.Mode.REGULAR;

/**
 * Created by: Alex Kucherenko
 * Date: 22.02.2017.
 */

public class EmptyPlayerModeModel implements PlayerMode.Model {

	public EmptyPlayerModeModel() {
	}

	@Override
	public PlayerMode.Mode getMode() {
		return REGULAR;
	}

	@Override
	public void init() {
	}

	@Override
	public void release() {
	}

	@Override
	public void setListener(Listener listener) {
	}


}
