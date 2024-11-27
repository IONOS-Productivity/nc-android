/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.exception

import android.net.Uri

internal class ImportPictureException(val pictureUri: Uri, cause: Throwable) : Exception(cause)
