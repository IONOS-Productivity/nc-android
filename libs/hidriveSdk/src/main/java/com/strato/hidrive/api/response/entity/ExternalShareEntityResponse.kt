package com.strato.hidrive.api.response.entity

/**
 * Created by Sergey Shandyuk on 5/30/2018.
 */
data class ExternalShareEntityResponse(var id: String?,
									   var label: String?,
									   var type: String?,
									   var status: String?,
									   var password: String?,
									   var has_password: Boolean?,
									   var writable: Boolean?,
									   var created: String?)