/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.pager.adapter;

import com.ionos.player.ui.pager.PlayerPagerFragmentFactory;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DefaultFragmentPagerAdapter<T> extends AbstractFragmentPagerAdapter<T> {
    private final PlayerPagerFragmentFactory<T> fragmentFactory;

    public DefaultFragmentPagerAdapter(FragmentManager fragmentManager, PlayerPagerFragmentFactory<T> fragmentFactory) {
        super(fragmentManager);
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public int getEntityIndex(T entity) {
        return getItems().indexOf(entity);
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentFactory.create(this.items.get(position));
    }

    @Override
    protected T getLinkedItem(int position) {
        return this.items.get(position);
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
