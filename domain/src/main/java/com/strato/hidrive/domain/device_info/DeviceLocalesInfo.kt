package com.strato.hidrive.domain.device_info

import java.util.Locale

interface DeviceLocalesInfo {
	fun getFirstMatch(supportedLocales: Array<String>): Locale?
}
