package com.ionos.player.player_mode;

/**
 * Created by: Alex Kucherenko
 * Date: 23.02.2017.
 */

public interface SwitchModeStrategy {
	void doOnSwitchToMode(PlayerMode.Mode mode);
}