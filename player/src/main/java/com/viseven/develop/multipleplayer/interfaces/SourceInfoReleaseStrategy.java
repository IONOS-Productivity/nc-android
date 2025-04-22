package com.viseven.develop.multipleplayer.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * User: zuzik
 * Date: 8/27/16
 */
public interface SourceInfoReleaseStrategy<SourceInfo> extends Serializable {
	boolean releaseCurrentPlayback(
			List<SourceInfo> newSourceInfos,
			SourceInfo currentSourceInfo);
}
