package com.strato.hidrive.domain.permission

data class CheckPermissionsResult(
	val grantedPermissions: Collection<Permission>,
	val notGrantedPermissions: Collection<Permission>,
) {
	val isAllPermissionsGranted: Boolean get() = notGrantedPermissions.isEmpty()
	val isMandatoryPermissionsGranted: Boolean get() = notGrantedPermissions.none { it.isMandatory }
}
