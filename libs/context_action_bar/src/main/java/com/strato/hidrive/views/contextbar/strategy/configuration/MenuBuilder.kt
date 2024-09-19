package com.strato.hidrive.views.contextbar.strategy.configuration

import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemType
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemViewType

interface MenuBuilder {

	fun toolbar(item: ToolbarItemType): MenuBuilder

	fun toolbar(item: ToolbarItemType, itemViewType: ToolbarItemViewType): MenuBuilder

	fun toolbarText(item: ToolbarItemType): MenuBuilder

	fun fab(item: ToolbarItemType): MenuBuilder

	fun build(): ICABConfigurationStrategy
}
