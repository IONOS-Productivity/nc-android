/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.di

import com.ionos.scanbot.di.NCScanbotModule
import dagger.Module

@Module(includes = [NCScanbotModule::class])
abstract class StratoModule