/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.common

interface PlayerBasePresenter {

    fun onCreate()

    fun onDestroy()

    fun onAppear()

    fun onDisappear()
}