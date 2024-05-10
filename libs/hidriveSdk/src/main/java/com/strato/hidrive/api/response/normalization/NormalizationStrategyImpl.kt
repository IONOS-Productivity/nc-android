package com.strato.hidrive.api.response.normalization

import java.net.URLDecoder

/**
 * User: Dima Muravyov
 * Date: 26.02.2018
 */
class NormalizationStrategyImpl(private val defaultString: String = "",
								private val defaultInt: Int = 0,
								private val defaultLong: Long = 0,
								private val defaultDouble: Double = 0.0,
								private val defaultBoolean: Boolean = false)
	: NormalizationStrategy {

	override fun normalize(value: String?): String = value ?: defaultString

	override fun normalizeUrl(value: String?): String = value
			?.let { runCatching {  URLDecoder.decode(it, "utf-8") }.getOrNull() }
			?: defaultString

	override fun normalize(value: Int?): Int = value ?: defaultInt

	override fun normalize(value: Long?): Long = value ?: defaultLong

	override fun normalize(value: Boolean?): Boolean = value ?: defaultBoolean

	override fun normalize(value: Double?): Double = value ?: defaultDouble

	override fun <T : Any> normalize(value: List<T>?): List<T> = value ?: emptyList()
}