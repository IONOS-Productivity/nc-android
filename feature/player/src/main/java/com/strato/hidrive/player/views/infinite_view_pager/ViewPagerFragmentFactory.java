package com.strato.hidrive.player.views.infinite_view_pager;

import androidx.fragment.app.Fragment;

/**
 * Created by Anton Shevchuk on 31.10.2016.
 */

public interface ViewPagerFragmentFactory<T>{
	Fragment create(T t);
}