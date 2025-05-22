package com.ionos.player.ui.pager;

import androidx.fragment.app.Fragment;

/**
 * Created by Anton Shevchuk on 31.10.2016.
 */

public interface ViewPagerFragmentFactory<T>{
	Fragment create(T t);
}