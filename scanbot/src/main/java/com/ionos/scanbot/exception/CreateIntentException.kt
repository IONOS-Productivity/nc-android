/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.exception

import kotlin.reflect.KClass

internal class CreateIntentException(
	targetClass: KClass<*>,
) : Exception("${targetClass.java.simpleName} should be created via createIntent")
