package com.strato.hidrive.scanbot.license.gateway

import com.strato.hidrive.api.response.entity_response_transformer.ResponseTransformer
import okhttp3.ResponseBody

/**
 * User: Dima Muravyov
 * Date: 06.02.2020
 */
internal class LicenseResponseTransformer : ResponseTransformer<ResponseBody, String?> {

	companion object {
		private const val SEPARATOR = "\""
		private const val ESCAPED_NEW_LINE = "\\n"
		private const val NEW_LINE = "\n"
	}

	override fun transform(response: ResponseBody): String? {
		val inlinedResponse = String(response.bytes())

		var scanbotLicenseKey = ""
		var startIndex = inlinedResponse.indexOf(SEPARATOR)
		while (startIndex != -1) {
			val endIndex = inlinedResponse.indexOf(SEPARATOR, startIndex + 1)
			if (endIndex == -1) {
				scanbotLicenseKey = ""
				break
			}

			scanbotLicenseKey += inlinedResponse.substring(startIndex + 1, endIndex)

			startIndex = inlinedResponse.indexOf(SEPARATOR, endIndex + 1)
		}

		scanbotLicenseKey = scanbotLicenseKey.replace(ESCAPED_NEW_LINE, NEW_LINE)

		return if (scanbotLicenseKey.isNotBlank()) scanbotLicenseKey else null
	}
}