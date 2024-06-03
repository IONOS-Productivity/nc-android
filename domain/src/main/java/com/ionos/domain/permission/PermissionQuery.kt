package com.ionos.domain.permission

interface PermissionQuery {

	fun checkPermissions(vararg permissions: Permission, listener: PermissionsListener)
}
