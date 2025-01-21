/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.upload.target

import com.ionos.scanbot.upload.target_provider.UploadTarget

class RemoteUploadTarget(
    override val uploadPath: String
) : UploadTarget