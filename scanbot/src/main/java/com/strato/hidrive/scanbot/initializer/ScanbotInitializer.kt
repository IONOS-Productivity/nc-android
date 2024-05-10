package com.strato.hidrive.scanbot.initializer

interface ScanbotInitializer {

	fun initialize()

	fun isInitialized(): Boolean

	fun isLicenseValid(): Boolean
}
