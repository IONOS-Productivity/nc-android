/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.upload.target

import com.ionos.scanbot.upload.target_provider.UploadTarget

class RemoteUploadTarget(
    override val uploadPath: String
) : UploadTarget