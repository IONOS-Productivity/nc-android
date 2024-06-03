package com.ionos.domain.permission

fun interface PermissionsListener {
	fun onResult(result: CheckPermissionsResult)
}
