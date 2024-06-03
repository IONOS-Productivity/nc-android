package com.ionos.domain.utils

class Result<T : Any> private constructor(
	private val value: T?,
	private val error: Throwable?,
) {
	val isSuccess: Boolean get() = value != null
	val isError: Boolean get() = error != null

	fun getOrNull(): T? = value

	fun getOrElse(defaultValue: T): T = value ?: defaultValue

	fun errorOrNull(): Throwable? = error

	companion object {
		@JvmStatic
		fun <T : Any> success(value: T): Result<T> = Result(value, null)

		@JvmStatic
		fun <T : Any> error(error: Throwable): Result<T> = Result(null, error)
	}
}
