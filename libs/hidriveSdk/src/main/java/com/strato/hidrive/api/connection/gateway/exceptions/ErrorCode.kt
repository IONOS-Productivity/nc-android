package com.strato.hidrive.api.connection.gateway.exceptions

/**
 * Created by: Alex Kucherenko
 * Date: 27.03.2018.
 */
enum class ErrorCode(private val code: Int) {
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	FORBIDDEN(403),
	NOT_FOUND(404),
	CONFLICT(409),
	REQUEST_ENTITY_TOO_LARGE(413),
	UNSUPPORTED_MEDIA_TYPE(415),
	RANGE_NOT_SATISFIABLE(416),
	UNPROCESSABLE_ENTITY(422),
	INTERNAL_ERROR(500),
	SERVICE_UNAVAILABLE(503),
	GATEWAY_TIMEOUT(504),
	INSUFFICIENT_STORAGE(507),
	UNKNOWN_ERROR(0);

	companion object {
		fun byCode(code: Int) = ErrorCode
				.values()
				.filter {
					it.code == code
				}
				.firstOrNull() ?: UNKNOWN_ERROR
	}
}