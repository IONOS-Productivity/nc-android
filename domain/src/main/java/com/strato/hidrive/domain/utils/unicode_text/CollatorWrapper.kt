package com.strato.hidrive.domain.utils.unicode_text

fun interface CollatorWrapperFactory {
	fun create(): CollatorWrapper
}

interface CollatorWrapper : Comparator<String> {

	fun getCollationKeyByteArray(source: String): ByteArray

}