package com.strato.hidrive.common_ui.view.contextbar;

import com.strato.hidrive.common_ui.view.contextbar.strategy.configuration.ICABConfigurationStrategy;

/**
 * Created by yaz on 8/17/16.
 */
public interface ToolbarView {
	void setToolbarItemClickListener(ToolbarItemClickListener listener);

	void setToolbarStrategy(ICABConfigurationStrategy strategy);
}
