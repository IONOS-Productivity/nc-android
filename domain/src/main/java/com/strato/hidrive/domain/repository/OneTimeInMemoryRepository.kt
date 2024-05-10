package com.strato.hidrive.domain.repository

import com.strato.hidrive.domain.optional.Optional

/**
 * Created by: Alex Kucherenko
 * Date: 29.11.2018.
 */
open class OneTimeInMemoryRepository<T> : DeleteOnAccessedRepository<T> {
	private var data: Optional<T> = Optional.absent()

	override fun write(data: T) {
		this.data = Optional.of(data)
	}

	override fun read(): Optional<T> {
		val tmpData = data
		data = Optional.absent()
		return tmpData
	}

	override fun peek(): T? = data.orNull()

}