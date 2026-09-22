/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO GmbH.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.audio

import androidx.fragment.app.Fragment
import com.ionos.player.model.PlaybackFile
import com.ionos.player.ui.pager.PlayerPagerFragmentFactory

class AudioFileFragmentFactory : PlayerPagerFragmentFactory<PlaybackFile> {

    override fun create(item: PlaybackFile): Fragment {
        return AudioFileFragment.createInstance(item)
    }
}
