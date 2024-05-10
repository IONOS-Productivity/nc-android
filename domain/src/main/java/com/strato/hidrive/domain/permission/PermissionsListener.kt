package com.strato.hidrive.domain.permission

fun interface PermissionsListener {
	fun onResult(result: CheckPermissionsResult)
}
