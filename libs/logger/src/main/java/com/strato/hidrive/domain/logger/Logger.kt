package com.strato.hidrive.domain.logger

interface Logger {
	fun logD(tag: String, message: String)

	fun logD(message: String?, t: Throwable?)

	fun logI(tag: String, message: String)

	fun logW(message: String?, t: Throwable?)

	fun logW(tag: String, message: String)

	fun logE(message: String?, t: Throwable?)

	fun logE(tag: String, message: String)
}