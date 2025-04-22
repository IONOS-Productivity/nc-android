package com.strato.hidrive.player;

import com.strato.hidrive.player.domain.PlayerFileInfo;
import com.viseven.develop.multipleplayermvp.interfaces.ControlAvailabilityStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public class HiDriveCyclicNextControlAvailabilityStrategy implements ControlAvailabilityStrategy<PlayerFileInfo> {
	@Override
	public boolean available(List<PlayerFileInfo> sourceInfos, PlayerFileInfo currentSourceInfo, boolean shuffle) {
		return !(sourceInfos.isEmpty() || sourceInfos.size() == 1);
	}
}
