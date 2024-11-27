/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.di

import com.ionos.scanbot.di.NCScanbotModule
import dagger.Module

@Module(includes = [NCScanbotModule::class])
abstract class StratoModule