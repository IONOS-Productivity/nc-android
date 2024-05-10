package com.strato.hidrive.scanbot.util.lifedata

import androidx.lifecycle.LiveData

internal abstract class StateLiveData<S : Any>(initialValue: S) : LiveData<S>(initialValue) {

	override fun getValue(): S {
		return super.getValue() ?: throw IllegalStateException()
	}

	operator fun invoke(): S = getValue()
}
