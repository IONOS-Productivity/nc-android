package com.strato.hidrive.views.contextbar.toolbar.views

import android.content.Context
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemViewType

interface ToolbarItemViewFactory {

	fun registerCustomTypeFactory(type: ToolbarItemViewType, factory: SingleTypeToolbarItemViewFactory)

	fun create(context: Context, item: ToolbarItem): ToolbarItemView
}
