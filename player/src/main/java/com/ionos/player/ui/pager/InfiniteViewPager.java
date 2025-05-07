package com.ionos.player.ui.pager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ionos.player.R;
import com.ionos.player.ui.pager.adapter.AbstractFragmentPagerAdapter;
import com.ionos.player.ui.pager.adapter.DefaultFragmentViewPagerAdapter;
import com.ionos.player.ui.pager.adapter.InfinityFragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by yaz on 1/17/17.
 */

public class InfiniteViewPager<T> extends LinearLayout {

	private final ViewPager viewPager;
	private ModeStrategy<T> modeStrategy;
	private AbstractFragmentPagerAdapter<T> adapter;
	private InfiniteViewPagerListener<T> infiniteViewPagerListener = NullInfiniteViewPagerListener.getInstance();
	private final CompositeViewPagerOnPageChangeListener compositeViewPagerOnPageChangeListener = new CompositeViewPagerOnPageChangeListener();
	private int currentPosition = -1;
	private int shift = -1;
	private int restoredShift = -1;

	private ViewPager.OnPageChangeListener listener;

	public InfiniteViewPager(Context context) {
		this(context, null);
	}

	public InfiniteViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.view_infinite_pager, this);
		this.viewPager = findViewById(R.id.viewPager);
		listener = nullOnPageChangeListener;
	}

	public void setInfiniteViewPagerListener(InfiniteViewPagerListener<T> infiniteViewPagerListener) {
		this.infiniteViewPagerListener = infiniteViewPagerListener != null ? infiniteViewPagerListener : NullInfiniteViewPagerListener.getInstance();
	}

	public void addViewPagerOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		this.listener = listener;
		this.compositeViewPagerOnPageChangeListener.addListener(this.listener);
	}

	public void removeViewPagerOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		this.compositeViewPagerOnPageChangeListener.removeListener(listener);
	}

	public int getCurrentPosition() {
		return this.viewPager.getCurrentItem();
	}

	public T getCurrentItem() {
		return adapter.getItemForPosition(getCurrentPosition());
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable state = super.onSaveInstanceState();
		InfiniteViewPagerState infiniteViewPagerState = new InfiniteViewPagerState(state);
		infiniteViewPagerState.shiftedPosition = this.shift;
		return infiniteViewPagerState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		InfiniteViewPagerState restoredState = (InfiniteViewPagerState) state;
		super.onRestoreInstanceState(restoredState.getSuperState());

		this.restoredShift = restoredState.shiftedPosition;
	}

	public void init(FragmentManager fragmentManager, Mode mode, ViewPagerFragmentFactory<T> fragmentFactory) {
		this.modeStrategy = createModeStrategy(mode);
		this.adapter = this.modeStrategy.createAdapter(fragmentManager, fragmentFactory);
		this.viewPager.setAdapter(this.adapter);
		this.compositeViewPagerOnPageChangeListener.addListener(this.modeStrategy.createListener());
	}

	private ModeStrategy<T> createModeStrategy(Mode mode) {
		switch (mode) {
			case FINITE:
				return new FiniteModeStrategy();
			case INFINITE:
				return new InfiniteModeStrategy();
			default:
				throw new RuntimeException("Unknown value " + mode);
		}
	}

	public List<T> getItems() {
		return this.adapter.getItems();
	}

	public void setItems(List<T> items) {
		final boolean shiftHasBeenRestored = restoredShift != -1;
		if (shiftHasBeenRestored) {
			items = shiftRestoredPosition(items);
		}

		final int calculatedCurrentPositionWithOffsetIfNeeded = modeStrategy.getCurrentPosition(adapter.getCount(), currentPosition);
		T currentItem = null;
		if (calculatedCurrentPositionWithOffsetIfNeeded >= 0 && currentItemPositionsNotTheSameAfterShuffleMatch(calculatedCurrentPositionWithOffsetIfNeeded)) {
			currentItem = adapter.getItems().get(calculatedCurrentPositionWithOffsetIfNeeded);
			items = calculateShiftAndRotateList(items, calculatedCurrentPositionWithOffsetIfNeeded, currentItem);
		}

		this.adapter.setItems(items);
		if (currentItem != null) {
			this.adapter.setCurrentItem(!items.isEmpty() ? currentItem : null);
		}
		notifyDataSetChangedWithoutCallingListener();
		setCurrentItem(currentItem, false);
	}

	private List<T> calculateShiftAndRotateList(List<T> items, int calculatedCurrentPositionWithOffsetForInfinityStrategy, T currentItem) {
		this.shift = calculateShift(items, calculatedCurrentPositionWithOffsetForInfinityStrategy, currentItem);
		items = rotate(items, this.shift);
		return items;
	}

	private void notifyDataSetChangedWithoutCallingListener() {
		this.viewPager.removeOnPageChangeListener(this.compositeViewPagerOnPageChangeListener);
		this.adapter.notifyDataSetChanged();
		this.viewPager.addOnPageChangeListener(this.compositeViewPagerOnPageChangeListener);
	}

	private boolean currentItemPositionsNotTheSameAfterShuffleMatch(int calculatedCurrentPosition) {
		return !this.adapter.getItems().isEmpty() &&
				this.currentPosition >= 0 &&
				calculatedCurrentPosition < this.adapter.getItems().size();
	}

	private List<T> shiftRestoredPosition(List<T> items) {
		this.shift = this.restoredShift;
		items = rotate(items, this.shift);
		this.restoredShift = -1;
		return items;
	}

	static int calculateShift(List items, int calculatedCurrentPosition, Object currentItem) {
		int newCurrentItemIndex = items.indexOf(currentItem);
		if (newCurrentItemIndex >= 0) {
			if (newCurrentItemIndex != calculatedCurrentPosition) {
				return items.size() - newCurrentItemIndex + calculatedCurrentPosition;
			}
		}
		return 0;
	}

	static <T> List<T> rotate(List<T> list, int shift) {
		List<T> newValues = new ArrayList<>(list);
		Collections.rotate(newValues, shift);
		return newValues;
	}

	public void setCurrentItem(T item) {
		setCurrentItem(item, true);
	}

	private void setCurrentItem(T item, boolean smoothScroll) {
		this.currentPosition = this.adapter.getEntityIndex(item);
		if (this.currentPosition != -1 && this.viewPager.getCurrentItem() != this.currentPosition) {
			this.viewPager.removeOnPageChangeListener(this.compositeViewPagerOnPageChangeListener);
			this.viewPager.setCurrentItem(this.currentPosition, smoothScroll);
			this.viewPager.addOnPageChangeListener(this.compositeViewPagerOnPageChangeListener);
		}
	}

	public void setCurrentItem(int position) {
		this.viewPager.setCurrentItem(position);
	}

	//region OnPageChangeListener

	public class DefaultOnPageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			infiniteViewPagerListener.onSwitchToItem(adapter.getItemForPosition(position));
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	public class InfinityOnPageChangeListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			if (position == 0) {
				compositeViewPagerOnPageChangeListener.removeListener(listener);
				viewPager.setCurrentItem(adapter.getCount() - 2, false);
				compositeViewPagerOnPageChangeListener.addListener(listener);
				return;
			}

			if (position >= adapter.getCount() - 1) {
				compositeViewPagerOnPageChangeListener.removeListener(listener);
				viewPager.setCurrentItem(1, false);
				compositeViewPagerOnPageChangeListener.addListener(listener);
				return;
			}
			infiniteViewPagerListener.onSwitchToItem(adapter.getItemForPosition(position));
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
	//endregion

	//region Strategy

	private interface ModeStrategy<T> {
		AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, ViewPagerFragmentFactory<T> fragmentFactory);

		ViewPager.OnPageChangeListener createListener();

		int getCurrentPosition(int itemCount, int position);
	}

	private class FiniteModeStrategy implements ModeStrategy<T> {
		@Override
		public AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, ViewPagerFragmentFactory<T> fragmentFactory) {
			return new DefaultFragmentViewPagerAdapter<>(fragmentManager, fragmentFactory);
		}

		@Override
		public ViewPager.OnPageChangeListener createListener() {
			return new DefaultOnPageChangeListener();
		}

		@Override
		public int getCurrentPosition(int itemCount, int position) {
			return position;
		}
	}

	private class InfiniteModeStrategy implements ModeStrategy<T> {
		@Override
		public AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, ViewPagerFragmentFactory<T> fragmentFactory) {
			return new InfinityFragmentViewPagerAdapter<>(fragmentManager, fragmentFactory);
		}

		@Override
		public ViewPager.OnPageChangeListener createListener() {
			return new InfinityOnPageChangeListener();
		}

		@Override
		public int getCurrentPosition(int itemCount, int position) {
			return itemCount > 1 ? position - 1 : position;
		}
	}
//endregion

	private static class InfiniteViewPagerState extends BaseSavedState {
		private int shiftedPosition;

		InfiniteViewPagerState(Parcelable superState) {
			super(superState);
		}

		private InfiniteViewPagerState(Parcel in) {
			super(in);
			this.shiftedPosition = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(this.shiftedPosition);
		}

		public static final Parcelable.Creator<InfiniteViewPagerState> CREATOR =
				new Parcelable.Creator<InfiniteViewPagerState>() {
					public InfiniteViewPagerState createFromParcel(Parcel in) {
						return new InfiniteViewPagerState(in);
					}

					public InfiniteViewPagerState[] newArray(int size) {
						return new InfiniteViewPagerState[size];
					}
				};
	}

	private final ViewPager.OnPageChangeListener nullOnPageChangeListener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};
}
