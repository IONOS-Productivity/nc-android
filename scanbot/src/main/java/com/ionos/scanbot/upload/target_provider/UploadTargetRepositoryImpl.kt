/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.upload.target_provider

class UploadTargetRepositoryImpl(initialVue: UploadTarget) : UploadTargetRepository {
	override var uploadTarget: UploadTarget = initialVue
}
