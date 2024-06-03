package com.strato.hidrive.scanbot.license

interface KeyStore {

	operator fun get(keyId: String): String?

	operator fun set(keyId: String, keyValue: String?)
}
