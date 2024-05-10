package com.strato.hidrive.util

import com.strato.hidrive.api.connection.apiclient.ApiClientWrapper
import kotlin.reflect.KClass

fun <T : Any> ApiClientWrapper.create(service: KClass<T>): T {
	return client.create(service.java)
}
