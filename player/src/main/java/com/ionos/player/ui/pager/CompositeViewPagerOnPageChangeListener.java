package com.ionos.player.ui.pager;

import java.util.HashSet;
import java.util.Set;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by yaz on 1/18/17.
 */

public class CompositeViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

	private final Set<ViewPager.OnPageChangeListener> listeners = new HashSet<>();

	public void addListener(ViewPager.OnPageChangeListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(ViewPager.OnPageChangeListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		for (ViewPager.OnPageChangeListener listener : new HashSet<>(this.listeners)) {
			listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position) {
		for (ViewPager.OnPageChangeListener listener : new HashSet<>(this.listeners)) {
			listener.onPageSelected(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		for (ViewPager.OnPageChangeListener listener : new HashSet<>(this.listeners)) {
			listener.onPageScrollStateChanged(state);
		}
	}
}
