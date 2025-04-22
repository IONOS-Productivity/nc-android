package com.viseven.develop.multipleplayer.player_source_release_strategy;

import com.viseven.develop.multipleplayer.interfaces.SourceInfoReleaseStrategy;

import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public class DoNotReleaseSourceInfoReleaseStrategy<SourceInfo> implements SourceInfoReleaseStrategy<SourceInfo> {
	@Override
	public boolean releaseCurrentPlayback(List<SourceInfo> newSourceInfos, SourceInfo currentSourceInfo) {
		return false;
	}
}
