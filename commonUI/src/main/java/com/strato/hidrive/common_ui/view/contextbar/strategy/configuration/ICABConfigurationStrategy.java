package com.strato.hidrive.common_ui.view.contextbar.strategy.configuration;


import com.strato.hidrive.common_ui.view.contextbar.strategy.mode.ICABModeStrategy;
import com.strato.hidrive.common_ui.view.contextbar.strategy.popup_header.PopupHeaderBundle;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces.ToolbarImageMapper;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces.ToolbarTextMapper;
import com.strato.hidrive.domain.optional.Optional;

import java.util.List;

public interface ICABConfigurationStrategy {

	ICABModeStrategy getCABModeStrategy();

	List<ToolbarItem> getToolbarItems();

	Optional<PopupHeaderBundle> getPopupHeaderBundle();

	ToolbarTextMapper getTextMapper();

	ToolbarImageMapper getImageMapper();
}
