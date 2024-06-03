package com.ionos.logger

object LoggerUtil {
	private var logger: Logger? = null

	@JvmStatic
	fun initialise (logger: Logger) {
		LoggerUtil.logger = logger
	}

	@JvmStatic
	fun logE(message: String, t: Throwable?){
		logger?.logE(message, t)
	}

	@JvmStatic
	fun logE(tag: String, message: String){
		logger?.logE(tag, message)
	}

	@JvmStatic
	fun logD(tag: String, message: String){
		logger?.logD(tag, message)
	}

	@JvmStatic
	fun logD(message: String, t: Throwable?){
		logger?.logD(message, t)
	}

	@JvmStatic
	fun logW(tag: String, message: String){
		logger?.logW(tag, message)
	}

	@JvmStatic
	fun logW(message: String, t: Throwable?){
		logger?.logW(message, t)
	}

	@JvmStatic
	fun logI(tag: String, message: String){
		logger?.logI(tag, message)
	}
}