package com.strato.hidrive.api.response.entity

/**
 * User: Dima Muravyov
 * Date: 14.05.2018
 */
data class RshareResponse(val id: String?,
						  val status: String?,
						  val readable: Boolean?,
						  val writable: Boolean?,
						  val count: Long?,
						  val maxcount: Long?,
						  val password: String?,
						  val created: Long?,
						  val last_modified: Long?,
						  val share_type: String?,
						  val is_encrypted: Boolean?)