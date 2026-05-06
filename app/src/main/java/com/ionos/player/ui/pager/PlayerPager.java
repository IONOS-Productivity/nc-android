/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ionos.player.ui.pager.adapter.AbstractFragmentPagerAdapter;
import com.ionos.player.ui.pager.adapter.DefaultFragmentPagerAdapter;
import com.ionos.player.ui.pager.adapter.InfiniteFragmentPagerAdapter;
import com.ionos.player.ui.pager.listener.NullPlayerPagerListener;
import com.ionos.player.ui.pager.listener.OnPageChangeCompositeListener;
import com.ionos.player.ui.pager.listener.PlayerPagerListener;
import com.owncloud.android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class PlayerPager<T> extends LinearLayout {

    private final ViewPager viewPager;
    private ModeStrategy<T> modeStrategy;
    private AbstractFragmentPagerAdapter<T> adapter;
    private PlayerPagerListener<T> playerPagerListener = NullPlayerPagerListener.getInstance();
    private final OnPageChangeCompositeListener onPageChangeCompositeListener = new OnPageChangeCompositeListener();
    private int currentPosition = -1;
    private int shift = -1;
    private int restoredShift = -1;

    private ViewPager.OnPageChangeListener listener;

    public PlayerPager(Context context) {
        this(context, null);
    }

    public PlayerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.player_pager, this);
        this.viewPager = findViewById(R.id.viewPager);
        listener = nullOnPageChangeListener;
    }

    public void setPlayerPagerListener(PlayerPagerListener<T> playerPagerListener) {
        this.playerPagerListener = playerPagerListener != null ? playerPagerListener : NullPlayerPagerListener.getInstance();
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.listener = listener;
        this.onPageChangeCompositeListener.addListener(this.listener);
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.onPageChangeCompositeListener.removeListener(listener);
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

    public void init(FragmentManager fragmentManager, PlayerPagerMode mode, PlayerPagerFragmentFactory<T> fragmentFactory) {
        this.modeStrategy = createModeStrategy(mode);
        this.adapter = this.modeStrategy.createAdapter(fragmentManager, fragmentFactory);
        this.viewPager.setAdapter(this.adapter);
        this.onPageChangeCompositeListener.addListener(this.modeStrategy.createListener());
    }

    private ModeStrategy<T> createModeStrategy(PlayerPagerMode mode) {
        switch (mode) {
            case DEFAULT:
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
        this.viewPager.removeOnPageChangeListener(this.onPageChangeCompositeListener);
        this.adapter.notifyDataSetChanged();
        this.viewPager.addOnPageChangeListener(this.onPageChangeCompositeListener);
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
            this.viewPager.removeOnPageChangeListener(this.onPageChangeCompositeListener);
            this.viewPager.setCurrentItem(this.currentPosition, smoothScroll);
            this.viewPager.addOnPageChangeListener(this.onPageChangeCompositeListener);
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
            playerPagerListener.onSwitchToItem(adapter.getItemForPosition(position));
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
                onPageChangeCompositeListener.removeListener(listener);
                viewPager.setCurrentItem(adapter.getCount() - 2, false);
                onPageChangeCompositeListener.addListener(listener);
                return;
            }

            if (position >= adapter.getCount() - 1) {
                onPageChangeCompositeListener.removeListener(listener);
                viewPager.setCurrentItem(1, false);
                onPageChangeCompositeListener.addListener(listener);
                return;
            }
            playerPagerListener.onSwitchToItem(adapter.getItemForPosition(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
    //endregion

    //region Strategy

    private interface ModeStrategy<T> {
        AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, PlayerPagerFragmentFactory<T> fragmentFactory);

        ViewPager.OnPageChangeListener createListener();

        int getCurrentPosition(int itemCount, int position);
    }

    private class FiniteModeStrategy implements ModeStrategy<T> {
        @Override
        public AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, PlayerPagerFragmentFactory<T> fragmentFactory) {
            return new DefaultFragmentPagerAdapter<>(fragmentManager, fragmentFactory);
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
        public AbstractFragmentPagerAdapter<T> createAdapter(FragmentManager fragmentManager, PlayerPagerFragmentFactory<T> fragmentFactory) {
            return new InfiniteFragmentPagerAdapter<>(fragmentManager, fragmentFactory);
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
