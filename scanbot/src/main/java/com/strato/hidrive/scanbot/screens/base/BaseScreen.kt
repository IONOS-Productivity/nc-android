package com.strato.hidrive.scanbot.screens.base

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
