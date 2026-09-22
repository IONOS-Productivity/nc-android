/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model

class SourceException(
    errorCode: Int = 0,
) : Exception(
    "Source not found. Error code: $errorCode",
)
