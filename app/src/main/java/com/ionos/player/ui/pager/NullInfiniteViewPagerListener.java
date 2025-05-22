package com.ionos.player.ui.pager;

/**
 * Created by yaz on 1/17/17.
 */
class NullInfiniteViewPagerListener<T> implements InfiniteViewPagerListener<T> {

	private static final NullInfiniteViewPagerListener INSTANCE = new NullInfiniteViewPagerListener();

	public static <T> NullInfiniteViewPagerListener<T> getInstance() {
		return INSTANCE;
	}

	@Override
	public void onSwitchToItem(T item) {
	}
}
