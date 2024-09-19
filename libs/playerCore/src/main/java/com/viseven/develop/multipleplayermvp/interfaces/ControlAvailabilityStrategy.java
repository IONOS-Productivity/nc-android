package com.viseven.develop.multipleplayermvp.interfaces;

import java.util.List;

/**
 * User: zuzik
 * Date: 6/18/16
 */
public interface ControlAvailabilityStrategy<SourceInfo> {
	boolean available(
			List<SourceInfo> sourceInfos,
			SourceInfo currentSourceInfo,
			boolean shuffle);
}
