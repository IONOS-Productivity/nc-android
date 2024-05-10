package com.strato.hidrive.common_ui.stylized

import android.content.Context
import android.text.ParcelableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.strato.hidrive.common_ui.R
import kotlin.math.max
import kotlin.math.min

class StylizedEditText @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = android.R.attr.editTextStyle,
) : AppCompatEditText(context, attrs, defStyleAttr) {

	private val selectedTextSpan: ParcelableSpan?

	init {
		context.obtainStyledAttributes(attrs, R.styleable.StylizedEditText).let {
			selectedTextSpan = if (it.hasValue(R.styleable.StylizedEditText_selectedTextColor)) {
				ForegroundColorSpan(it.getColor(R.styleable.StylizedEditText_selectedTextColor, 0))
			} else {
				null
			}
			it.recycle()
		}
	}

	override fun onSelectionChanged(selStart: Int, selEnd: Int) {
		if (selectedTextSpan != null) {
			val spanStart = min(selStart, selEnd)
			val spanEnd = max(selStart, selEnd)
			if (spanEnd - spanStart > 0) {
				text?.setSpan(selectedTextSpan, spanStart, spanEnd, 0)
			} else {
				text?.removeSpan(selectedTextSpan)
			}
		}
		super.onSelectionChanged(selStart, selEnd)
	}
}
