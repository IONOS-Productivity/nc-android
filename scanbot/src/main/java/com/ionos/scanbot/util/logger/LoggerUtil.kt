package com.ionos.scanbot.util.logger

import com.ionos.scanbot.initializer.ScanbotInitializerImpl

object LoggerUtil {

	@JvmStatic
	fun logE(message: String, t: Throwable?){
		ScanbotInitializerImpl.logger?.logE(message, t)
	}

}