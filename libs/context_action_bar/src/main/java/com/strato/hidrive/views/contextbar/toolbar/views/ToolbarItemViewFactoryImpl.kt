package com.strato.hidrive.views.contextbar.toolbar.views

import android.content.Context
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemViewType

class ToolbarItemViewFactoryImpl : ToolbarItemViewFactory {
	private val typeFactories = mutableMapOf<ToolbarItemViewType, SingleTypeToolbarItemViewFactory>()

	init {
		typeFactories[ToolbarItemViewType.IMAGE] = ImageToolbarItemViewFactory()
		typeFactories[ToolbarItemViewType.TEXT] = TextToolbarItemViewFactory()
	}

	override fun registerCustomTypeFactory(type: ToolbarItemViewType, factory: SingleTypeToolbarItemViewFactory) {
		typeFactories[type] = factory
	}

	override fun create(context: Context, item: ToolbarItem): ToolbarItemView {
		val typeFactory = typeFactories[item.itemViewType]
		return typeFactory?.create(context, item)
			?: throw IllegalArgumentException("Unregistered ToolbarItemViewType: ${item.itemViewType.id}")
	}
}
