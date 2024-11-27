/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.screens.camera.use_case

import android.content.Context
import android.net.Uri
import com.ionos.scanbot.exception.OpenFileException
import javax.inject.Inject

internal class ReadContentToByteArray @Inject constructor(
	private val context: Context,
) {

	operator fun invoke(uri: Uri): ByteArray = context
		.contentResolver
		.openInputStream(uri)
		.let { it ?: throw OpenFileException(uri.path) }
		.buffered()
		.readBytes()
}