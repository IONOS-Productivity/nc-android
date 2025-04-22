package com.ionos.player.player_mode;

import com.ionos.player.domain.PlayerFileInfo;
import com.ionos.player.multipleplayermvp.interfaces.MultiplePlayer;

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
