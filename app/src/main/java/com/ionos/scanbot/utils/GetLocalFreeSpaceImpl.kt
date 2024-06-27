/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2024 Your Name <your@email.com>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.utils

import com.ionos.scanbot.util.GetLocalFreeSpace
import com.owncloud.android.utils.FileStorageUtils
import io.reactivex.Single
import javax.inject.Inject

class GetLocalFreeSpaceImpl @Inject constructor() : GetLocalFreeSpace {
    override fun invoke(): Single<Long> =
        Single.just(FileStorageUtils.getUsableSpace())

}