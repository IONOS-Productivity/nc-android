/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2024 STRATO AG.
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package com.ionos.scanbot.screens.base

import androidx.lifecycle.LiveData

internal interface BaseScreen {

	interface State<E : Event> {
		val event: E?
	}

	interface Event

	interface ViewModel<E : Event, S : State<E>> {
		val state: LiveData<S>

		fun onResume()

		fun onEventHandled()
	}
}
