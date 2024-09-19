package com.strato.hidrive.player.player_mode;

import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;

/**
 * Created by: Alex Kucherenko
 * Date: 23.02.2017.
 */

public class MultiplePlayerSwitchModeStrategy implements SwitchModeStrategy {

	private final MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> multiplePlayerModel;

	public MultiplePlayerSwitchModeStrategy(MultiplePlayer.Model<PlayerFileInfo, PlayerMode.Mode> multiplePlayerModel) {
		this.multiplePlayerModel = multiplePlayerModel;
	}

	@Override
	public void doOnSwitchToMode(PlayerMode.Mode mode) {
		this.multiplePlayerModel.switchToMode(mode);
	}
}
