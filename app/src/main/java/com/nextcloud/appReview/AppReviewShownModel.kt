/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2023 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2023 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later OR GPL-2.0-only
 */
package com.nextcloud.appReview

data class AppReviewShownModel(
    var firstShowYear: String?,
    var appRestartCount: Int,
    var reviewShownCount: Int,
    var lastReviewShownDate: String?
)
