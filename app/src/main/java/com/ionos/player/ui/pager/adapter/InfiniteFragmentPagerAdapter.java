/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager.adapter;

import com.ionos.player.ui.pager.PlayerPagerFragmentFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class InfiniteFragmentPagerAdapter<T> extends AbstractFragmentPagerAdapter<T> {

    private final PlayerPagerFragmentFactory<T> playerSourceFragmentFactory;

    public InfiniteFragmentPagerAdapter(FragmentManager fragmentManager, PlayerPagerFragmentFactory<T> playerSourceFragmentFactory) {
        super(fragmentManager);
        this.playerSourceFragmentFactory = playerSourceFragmentFactory;
    }

    @Override
    public Fragment getItem(int position) {
        return this.playerSourceFragmentFactory.create(this.items.get(position));
    }

    @Override
    protected T getLinkedItem(int position) {
        T linkedItem;
        if (position == 0) {
            linkedItem = getItems().get(getItems().size() - 1);
        } else if (position == getItems().size() + 1) {
            linkedItem = getItems().get(0);
        } else {
            linkedItem = getItems().get(position - 1);
        }
        return linkedItem;
    }

    @Override
    public List<T> getItems() {
        return this.items.size() > 1 ? removeStubs(this.items) : this.items;
    }

    @Override
    public void setItems(List<T> items) {
        this.items = new ArrayList<>(items);
        if (items.size() > 1) {
            this.items = addStubs(this.items);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getEntityIndex(T entity) {
        List<T> items = new ArrayList<>(this.items);
        if (items.size() > 1) {
            items = removeStubs(items);
            int songIndex = items.indexOf(entity);
            if (songIndex != -1) {
                return songIndex + 1;
            } else {
                return songIndex;
            }
        } else {
            return items.indexOf(entity);
        }
    }

    private List<T> addStubs(List<T> sources) {
        List<T> result = new ArrayList<>(sources);
        result.add(0, result.get(result.size() - 1));
        result.add(result.get(1));
        return result;
    }

    private List<T> removeStubs(List<T> sources) {
        List<T> result = new ArrayList<>(sources);
        result.remove(0);
        result.remove(result.size() - 1);
        return result;
    }
}
