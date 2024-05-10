package com.strato.hidrive.api.response.entity

/**
 * User: Dima Muravyov
 * Date: 27.02.2018
 */
data class ShareEntityResponse(val created: Long?,
							   val path: String?,
							   val ttl: Long?,
							   val maxcount: Long?,
							   val count: Long?,
							   val uri: String?,
							   val id: String?,
							   val last_modified: Long?,
							   val password: String?,
							   val status: String?,
							   val writable: Boolean?,
							   val share_type: String?,
							   val file_type: String?,
							   val pid: String?,
							   val valid_until: Long?,
							   val readable: Boolean?,
							   val remaining: Long?,
							   val has_password: Boolean?,
							   val name: String?,
							   val size: Long?,
							   val is_encrypted: Boolean?)