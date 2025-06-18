package com.ionos.player.ui.pager;

import androidx.fragment.app.Fragment;

public interface ViewPagerFragmentFactory<T>{
	Fragment create(T t);
}