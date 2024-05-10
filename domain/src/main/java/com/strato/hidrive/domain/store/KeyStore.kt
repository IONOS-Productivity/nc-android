package com.strato.hidrive.domain.store

interface KeyStore {

	operator fun get(keyId: String): String?

	operator fun set(keyId: String, keyValue: String?)
}
