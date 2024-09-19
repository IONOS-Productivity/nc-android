package com.strato.hidrive.views.contextbar;

import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy;
import com.strato.hidrive.views.contextbar.strategy.configuration.ToolbarItemClickListener;

/**
 * Created by yaz on 8/17/16.
 */
public interface ToolbarView {
	void setToolbarItemClickListener(ToolbarItemClickListener listener);

	void setToolbarStrategy(ICABConfigurationStrategy strategy);
}
