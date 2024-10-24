package com.ionos.scanbot.util.logger

interface Logger {

	fun logE(message: String, t: Throwable?)

}