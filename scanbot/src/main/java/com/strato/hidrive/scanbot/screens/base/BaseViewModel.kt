package com.strato.hidrive.scanbot.screens.base

import androidx.lifecycle.ViewModel
import com.strato.hidrive.scanbot.screens.base.BaseScreen.Event
import com.strato.hidrive.scanbot.screens.base.BaseScreen.State
import com.strato.hidrive.scanbot.tracking.ScanbotScreenEventTracker
import com.strato.hidrive.scanbot.util.lifedata.MutableStateLiveData
import com.strato.hidrive.scanbot.util.lifedata.StateLiveData
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseViewModel<E : Event, S : State<E>>(
	initialState: S,
	// private val eventTracker: ScanbotScreenEventTracker,
) : ViewModel(),
	BaseScreen.ViewModel<E, S> {

	override val state: StateLiveData<S> get() = _state
	private val _state = MutableStateLiveData(initialState)

	protected val subscriptions = CompositeDisposable()

	override fun onResume() {
		// eventTracker.trackPage()
	}

	override fun onCleared() {
		subscriptions.clear()
	}

	protected fun updateState(createUpdatedState: S.() -> S) {
		_state.update(createUpdatedState)
	}

	protected fun updateState(updatedState: S) {
		_state.setValue(updatedState)
	}
}
