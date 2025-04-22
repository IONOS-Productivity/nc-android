package com.viseven.develop.multipleplayermvp.interfaces;

/**
 * User: zuzik
 * Date: 7/4/16
 */
public interface MultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> {
	void onDestroy(MultiplePlayer.Model<SourceInfo, Mode> model);
}
