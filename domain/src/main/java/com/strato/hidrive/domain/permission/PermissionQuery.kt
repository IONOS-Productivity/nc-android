package com.strato.hidrive.domain.permission

interface PermissionQuery {

	fun checkPermissions(vararg permissions: Permission, listener: PermissionsListener)
}
