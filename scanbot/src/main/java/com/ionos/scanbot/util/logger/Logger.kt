/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.util.logger

interface Logger {

	fun logE(message: String, t: Throwable?)

}