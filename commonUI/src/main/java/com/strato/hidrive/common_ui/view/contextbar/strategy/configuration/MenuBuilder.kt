package com.strato.hidrive.common_ui.view.contextbar.strategy.configuration

import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItemType
import com.strato.hidrive.common_ui.view.contextbar.toolbar.ToolbarItemViewType

interface MenuBuilder {

	fun toolbar(item: ToolbarItemType): MenuBuilder

	fun toolbar(item: ToolbarItemType, itemViewType: ToolbarItemViewType): MenuBuilder

	fun toolbarText(item: ToolbarItemType): MenuBuilder

	fun fab(item: ToolbarItemType): MenuBuilder

	fun build(): ICABConfigurationStrategy
}
