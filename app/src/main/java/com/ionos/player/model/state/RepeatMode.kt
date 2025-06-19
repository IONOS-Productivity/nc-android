/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.model.state

import java.io.Serializable

enum class RepeatMode(val id: Int) : Serializable {
    OFF(0),
    SINGLE(1),
    ALL(2),
}
