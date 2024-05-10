package com.strato.hidrive.common_ui.view.filemanager.entity_view;

import com.strato.hidrive.common_ui.view.contextbar.strategy.configuration.ICABConfigurationStrategy;

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
