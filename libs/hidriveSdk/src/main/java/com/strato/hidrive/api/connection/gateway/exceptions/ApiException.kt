package com.strato.hidrive.api.connection.gateway.exceptions

/**
 * Created by: Alex Kucherenko
 * Date: 29.03.2018.
 */
class ApiException @JvmOverloads constructor(val errorCode: ErrorCode,
											 message: String?,
											 val error: String = "",
											 val errorDescription: String = "")
	: Exception(message)
