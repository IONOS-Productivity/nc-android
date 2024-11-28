/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.filter

import android.graphics.Bitmap
import io.scanbot.sdk.process.ImageProcessor


internal interface Filter {
	fun apply(imageProcessor: ImageProcessor, bitmap: Bitmap): Bitmap?
}