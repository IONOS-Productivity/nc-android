package com.viseven.develop.multipleplayer.interfaces;

import java.io.Serializable;

/**
 * Created by yaz on 1/23/17.
 */

public interface MultiplePlaybackErrorStrategy<SourceInfo, Mode> extends Serializable {
	boolean switchToNextSource(Throwable error, MultiplePlaybackState<SourceInfo, Mode> state);
}
