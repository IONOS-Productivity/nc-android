/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.owncloud.android.ui.adapter

import android.widget.TextView
import com.ionos.annotation.IonosCustomization
import com.ionos.player.ui.common.PlayerProgressIndicator

internal interface ListGridItemViewHolder : ListViewHolder {
    val fileName: TextView
    @IonosCustomization("Show current playback progress")
    val playerProgressIndicator: PlayerProgressIndicator
}
