package com.strato.hidrive.views.contextbar;


import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy;

/**
 * Created by Anton Shevchuk on 08.09.2016.
 */

public class EntityViewDisplayBundle {

	public final String navigationBarTitle;

	public final ICABConfigurationStrategy cabConfigurationStrategy;

	public final boolean inSelectMode;

	public EntityViewDisplayBundle(
			String navigationBarTitle,
			ICABConfigurationStrategy cabConfigurationStrategy,
			boolean inSelectMode) {
		this.navigationBarTitle = navigationBarTitle;
		this.cabConfigurationStrategy = cabConfigurationStrategy;
		this.inSelectMode = inSelectMode;
	}
}
