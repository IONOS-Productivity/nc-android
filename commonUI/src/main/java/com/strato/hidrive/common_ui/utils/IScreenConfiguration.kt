package com.strato.hidrive.common_ui.utils

import android.content.Context
import android.graphics.Point

/**
 * Created by Anton Shevchuk on 24.06.2016.
 */
interface IScreenConfiguration {

	fun large(context: Context): Boolean

	fun landscape(context: Context): Boolean

	fun realSize(context: Context): Point
}
