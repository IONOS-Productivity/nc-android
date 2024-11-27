/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.tracking

interface ScanbotFilterScreenEventTracker : ScanbotScreenEventTracker {

	fun trackBackPressed()

	fun trackSaveClicked()

	fun trackMoreClicked()

	fun trackApplyForAllClicked()

	fun trackMagicColorFilterApplied()

	fun trackMagicTextFilterApplied()

	fun trackColorFilterApplied()

	fun trackGrayscaleFilterApplied()

	fun trackBlackAndWhiteFilterApplied()

	fun trackFilterReset()

	fun trackBrightnessChanged()

	fun trackBrightnessReset()

	fun trackSharpnessChanged()

	fun trackSharpnessReset()

	fun trackContrastChanged()

	fun trackContrastReset()
}
