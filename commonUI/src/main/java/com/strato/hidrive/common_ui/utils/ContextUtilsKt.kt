package com.strato.hidrive.common_ui.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes

fun Resources.getFloatFromDimens(@DimenRes resId: Int): Float {
	val typedValue = TypedValue()
	getValue(resId, typedValue, true)
	return typedValue.float
}