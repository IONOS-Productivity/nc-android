/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.tracking

interface ScanbotGalleryScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackExitConfirmed()

	fun trackExitDenied()

	fun trackSwipeNext()

	fun trackSwipeBack()

	fun trackSaveClicked()

	fun trackAddPictureClicked()

	fun trackCropClicked()

	fun trackFilterClicked()

	fun trackRotateClicked()

	fun trackRearrangeClicked()

	fun trackDeleteClicked()
}
