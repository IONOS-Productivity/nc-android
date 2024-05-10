package com.strato.hidrive.common_ui.view.contextbar.strategy.configuration;

import com.strato.hidrive.common_ui.view.contextbar.strategy.mode.ICABModeStrategy;
import com.strato.hidrive.common_ui.view.contextbar.strategy.mode.NullCABModeStrategy;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.NullToolbarImageMapper;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.NullToolbarTextMapper;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces.ToolbarImageMapper;
import com.strato.hidrive.common_ui.view.contextbar.toolbar.interfaces.ToolbarTextMapper;
import com.strato.hidrive.domain.optional.Optional;
import com.strato.hidrive.common_ui.view.contextbar.strategy.popup_header.PopupHeaderBundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaz on 7/11/16.
 */
public class NullCABConfigurationStrategy implements ICABConfigurationStrategy {

	private static final NullCABConfigurationStrategy INSTANCE = new NullCABConfigurationStrategy();

	public static NullCABConfigurationStrategy getInstance() {
		return INSTANCE;
	}

	private NullCABConfigurationStrategy() {
	}

	@Override
	public Optional<PopupHeaderBundle> getPopupHeaderBundle() {
		return Optional.absent();
	}

	@Override
	public ICABModeStrategy getCABModeStrategy() {
		return NullCABModeStrategy.getInstance();
	}

	@Override
	public List<ToolbarItem> getToolbarItems() {
		return new ArrayList<>();
	}

	@Override
	public ToolbarTextMapper getTextMapper() {
		return NullToolbarTextMapper.INSTANCE;
	}

	@Override
	public ToolbarImageMapper getImageMapper() {
		return NullToolbarImageMapper.INSTANCE;
	}
}
