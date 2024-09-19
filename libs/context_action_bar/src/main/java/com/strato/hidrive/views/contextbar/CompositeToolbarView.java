package com.strato.hidrive.views.contextbar;

import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy;
import com.strato.hidrive.views.contextbar.strategy.configuration.ToolbarItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yaz on 8/17/16.
 */
public class CompositeToolbarView implements ToolbarView {

	private final List<ToolbarView> views;

	public CompositeToolbarView(ToolbarView... views) {
		this.views = Arrays.asList(views);
	}

	@Override
	public void setToolbarItemClickListener(ToolbarItemClickListener listener) {
		for (ToolbarView view : this.views) {
			if (view != null) {
				view.setToolbarItemClickListener(listener);
			}
		}
	}

	@Override
	public void setToolbarStrategy(ICABConfigurationStrategy strategy) {
		for (ToolbarView view : this.views) {
			if (view != null) {
				view.setToolbarStrategy(strategy);
			}
		}
	}
}
