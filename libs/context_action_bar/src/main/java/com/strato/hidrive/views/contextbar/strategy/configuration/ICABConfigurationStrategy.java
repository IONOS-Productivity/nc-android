package com.strato.hidrive.views.contextbar.strategy.configuration;


import com.strato.hidrive.views.contextbar.strategy.mode.ICABModeStrategy;
import com.strato.hidrive.views.contextbar.strategy.popup_header.PopupHeaderBundle;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.contextbar.toolbar.interfaces.ToolbarImageMapper;
import com.strato.hidrive.views.contextbar.toolbar.interfaces.ToolbarTextMapper;

import java.util.List;
import java.util.Optional;

public interface ICABConfigurationStrategy {

	ICABModeStrategy getCABModeStrategy();

	List<ToolbarItem> getToolbarItems();

	Optional<PopupHeaderBundle> getPopupHeaderBundle();

	ToolbarTextMapper getTextMapper();

	ToolbarImageMapper getImageMapper();
}
