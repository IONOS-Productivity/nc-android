package com.strato.hidrive.domain.repository

import com.strato.hidrive.domain.optional.Optional

/**
 * Created by: Alex Kucherenko
 * Date: 29.11.2018.
 */
interface Repository<T> {
	fun write(data: T)

	fun read(): Optional<T>
}

interface DeleteOnAccessedRepository<T> : Repository<T> {
	fun peek(): T?
}