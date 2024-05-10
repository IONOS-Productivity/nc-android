package com.strato.hidrive.api.response.normalization

/**
 * User: Dima Muravyov
 * Date: 26.02.2018
 */
interface NormalizationStrategy {

	fun normalize(value: String?): String

	fun normalizeUrl(value: String?): String

	fun normalize(value: Int?): Int

	fun normalize(value: Long?): Long

	fun normalize(value: Boolean?): Boolean

	fun normalize(value: Double?): Double

	fun <T : Any> normalize(value: List<T>?): List<T>
}