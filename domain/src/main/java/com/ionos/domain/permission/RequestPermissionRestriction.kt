package com.ionos.domain.permission

import kotlin.time.Duration

sealed interface RequestPermissionRestriction {

	object None : RequestPermissionRestriction

	data class OncePerPeriod(val period: Duration) : RequestPermissionRestriction
}
