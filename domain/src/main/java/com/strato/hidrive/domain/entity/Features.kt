package com.strato.hidrive.domain.entity

import java.io.Serializable

/**
 * User: Dima Muravyov
 * Date: 23.02.2018
 */
data class Features(val sharelinkPassword: Boolean,
					val sharelinkTimeToLive: Long,
					val sharelinkDownloads: Int,
					val backupAndRestoreEnabled: Boolean,
					val encryptionFeatureEnabled: Boolean,
					val premiumAccount: Boolean,
					val colaboraEnabled: Boolean,
) : Serializable