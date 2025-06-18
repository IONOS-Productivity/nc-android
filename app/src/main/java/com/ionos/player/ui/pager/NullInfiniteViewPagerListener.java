package com.ionos.player.ui.pager;

class NullInfiniteViewPagerListener<T> implements InfiniteViewPagerListener<T> {

	private static final NullInfiniteViewPagerListener INSTANCE = new NullInfiniteViewPagerListener();

	public static <T> NullInfiniteViewPagerListener<T> getInstance() {
		return INSTANCE;
	}

	@Override
	public void onSwitchToItem(T item) {
	}
}
