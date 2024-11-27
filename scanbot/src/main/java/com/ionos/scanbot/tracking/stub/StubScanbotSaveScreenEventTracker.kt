/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.tracking.stub

import com.ionos.scanbot.tracking.ScanbotSaveScreenEventTracker
import javax.inject.Inject

class StubScanbotSaveScreenEventTracker @Inject constructor(
) : ScanbotSaveScreenEventTracker {

    override fun trackBackPressed() = Unit

    override fun trackExitConfirmed() = Unit

    override fun trackExitDenied() = Unit

    override fun trackSaveClicked() = Unit

    override fun trackFileNameChanged() = Unit

    override fun trackSaveLocationPathClicked() = Unit

    override fun trackSaveLocationPathChanged() = Unit

    override fun trackSaveLocationPathChangeCanceled() = Unit

    override fun trackPdfOcrFileTypeSelected() = Unit

    override fun trackPdfFileTypeSelected() = Unit

    override fun trackJpgFileTypeSelected() = Unit

    override fun trackPngFileTypeSelected() = Unit

    override fun trackPage() = Unit
}