/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.di.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.*

/**
 * User: Alex Kucherenko
 * Date: 13.06.2019
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(FIELD, FUNCTION, PROPERTY, VALUE_PARAMETER)
annotation class ScanbotLicense