/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.scanbot.tracking

interface ScanbotCropScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackSaveClicked()

	fun trackDetectDocumentClicked()

	fun trackResetBordersClicked()
}
