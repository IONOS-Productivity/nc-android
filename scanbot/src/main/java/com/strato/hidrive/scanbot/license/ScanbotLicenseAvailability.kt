package com.strato.hidrive.scanbot.license

import com.strato.hidrive.domain.utils.availability.Availability
import com.strato.hidrive.scanbot.initializer.ScanbotInitializer
import javax.inject.Inject

/**
 * User: Alex Kucherenko
 * Date: 13.06.2019
 */
class ScanbotLicenseAvailability @Inject internal constructor(
	private val initializer: ScanbotInitializer,
) : Availability {

	override fun available(): Boolean = initializer.isInitialized() && initializer.isLicenseValid()
}