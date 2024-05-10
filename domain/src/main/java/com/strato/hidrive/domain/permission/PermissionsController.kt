package com.strato.hidrive.domain.permission

interface PermissionsController {

	fun isPermissionsGranted(vararg permissions: Permission): Boolean

	fun checkPermissions(permission: Permission, listener: PermissionsListener)

	fun checkPermissions(vararg permissions: Permission, listener: PermissionsListener)
	
	fun shouldRequestPermission(permission: Permission): Boolean
}
