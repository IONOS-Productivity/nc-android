package com.viseven.develop.multipleplayermvp.presenter_destroy_strategy;

import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayer;
import com.viseven.develop.multipleplayermvp.interfaces.MultiplePlayerPresenterDestroyStrategy;

/**
 * User: zuzik
 * Date: 7/4/16
 */
public class DoNothingMultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> implements MultiplePlayerPresenterDestroyStrategy<SourceInfo, Mode> {
	@Override
	public void onDestroy(MultiplePlayer.Model<SourceInfo, Mode> model) {
		
	}
}
