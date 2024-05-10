package com.strato.hidrive.domain.utils.kotlin.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SynchronizedProperty<T>(private val getDefaultValue: () -> T) : ReadWriteProperty<Any, T> {
	private var value: T? = null

	override fun getValue(thisRef: Any, property: KProperty<*>): T {
		return synchronized(this) {
			val currentValue = this.value
			if (currentValue != null) {
				currentValue
			} else {
				val defaultValue = this.getDefaultValue()
				this.value = defaultValue
				defaultValue
			}
		}
	}

	override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
		synchronized(this) {
			this.value = value
		}
	}
}
