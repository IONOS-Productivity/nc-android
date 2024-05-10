package com.strato.hidrive.api.connection.apiclient

import retrofit2.Retrofit

/**
 * Created by: Alex Kucherenko
 * Date: 11.04.2018.
 */
data class ApiClientWrapper(private val lazyClient: Lazy<Retrofit>) {
	val client: Retrofit by lazyClient
}