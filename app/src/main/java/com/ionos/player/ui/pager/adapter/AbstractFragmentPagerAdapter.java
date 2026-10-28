/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public abstract class AbstractFragmentPagerAdapter<T> extends FragmentStatePagerAdapter {

    protected List<T> items = new ArrayList<>();
    protected T currentItem;
    protected final List<Pair<T, Fragment>> cachedItems = new ArrayList<>();

    AbstractFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    abstract public int getEntityIndex(T entity);

    abstract public List<T> getItems();

    abstract public void setItems(List<T> items);

    abstract protected T getLinkedItem(int position);

    public void setCurrentItem(T item) {
        this.currentItem = item;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        T linkedItem = getLinkedItem(position);
        if (!findAndReplace(fragment, linkedItem)) {
            this.cachedItems.add(new Pair<>(linkedItem, fragment));
        }
        return fragment;
    }

    private boolean findAndReplace(Fragment fragment, T linkedItem) {
        for (int i = 0; i < this.cachedItems.size(); i++) {
            final Pair<T, Fragment> pair = this.cachedItems.get(i);
            if (pair.first.equals(linkedItem)) {
                Pair<T, Fragment> newPair = new Pair<>(pair.first, fragment);
                this.cachedItems.add(newPair);
                this.cachedItems.remove(pair);
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        for (int i = 0; i < this.cachedItems.size(); i++) {
            final Pair<T, Fragment> pair = this.cachedItems.get(i);
            if (pair.second.equals(object)) {
                this.cachedItems.remove(pair);
                break;
            }
        }
        super.destroyItem(container, position, object);
    }

    public T getItemForPosition(int position) {
        return this.items.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        for (int i = 0; i < this.cachedItems.size(); i++) {
            final Pair<T, Fragment> pair = this.cachedItems.get(i);
            if (pair.second.equals(object)) {
                if (this.currentItem != null && this.currentItem.equals(pair.first)) {
                    return super.getItemPosition(object);
                } else {
                    return POSITION_NONE;
                }
            }
        }
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }
}
